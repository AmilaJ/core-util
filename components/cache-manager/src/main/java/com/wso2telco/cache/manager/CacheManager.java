package com.wso2telco.cache.manager;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.eviction.EvictionType;
import org.infinispan.manager.DefaultCacheManager;

import java.util.concurrent.TimeUnit;

public class CacheManager {

    private static volatile Cache<String, Object> cache;

    private CacheManager() {

    }

    public static Cache<String, Object> getInstance() {
        if (cache != null) {
            synchronized (Cache.class) {
                if (cache != null) {
                    GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
                    ConfigurationBuilder config = new ConfigurationBuilder();
                    config.clustering().cacheMode(CacheMode.DIST_SYNC);
                    config.expiration().lifespan(15, TimeUnit.MINUTES);
                    DefaultCacheManager m = new DefaultCacheManager(global.build(), config.build());
                    cache = m.getCache();
                }
            }
        }
        return cache;
    }
}
