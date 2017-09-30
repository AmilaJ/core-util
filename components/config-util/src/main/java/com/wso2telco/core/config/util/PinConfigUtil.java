package com.wso2telco.core.config.util;

import com.wso2telco.cache.manager.CacheManager;
import com.wso2telco.core.config.model.PinConfig;
import org.infinispan.Cache;
import org.wso2.carbon.identity.application.authentication.framework.context.AuthenticationContext;

/**
 * Created by isuru on 1/2/17.
 */
public class PinConfigUtil {

    public static void savePinConfigToContext(AuthenticationContext authenticationContext, String msisdn,
                                              boolean isRegistering) {

        PinConfig pinConfig = new PinConfig();
        Cache<String, Object> cache = CacheManager.getInstance();

        if (isRegistering) {
            pinConfig.setCurrentStep(PinConfig.CurrentStep.REGISTRATION);
        } else {
            pinConfig.setCurrentStep(PinConfig.CurrentStep.LOGIN);
        }
        pinConfig.setInvalidFormatAttempts(0);
        pinConfig.setMsisdn(msisdn);
        pinConfig.setPinMismatchAttempts(0);
        pinConfig.setSessionId(authenticationContext.getContextIdentifier());
        pinConfig.setTotalAttempts(0);


        cache.put(authenticationContext.getContextIdentifier() + Constants.PIN_CONFIG_OBJECT, pinConfig);
    }

    public static void savePinConfigToContext(PinConfig pinConfig, AuthenticationContext authenticationContext) {
        Cache<String, Object> cache = CacheManager.getInstance();
        cache.put(authenticationContext.getContextIdentifier() + Constants.PIN_CONFIG_OBJECT, pinConfig);
    }

    public static PinConfig getPinConfig(AuthenticationContext authenticationContext) {
        Cache<String, Object> cache = CacheManager.getInstance();
        return (PinConfig) cache.get(authenticationContext.getContextIdentifier() + Constants.PIN_CONFIG_OBJECT);
    }

}
