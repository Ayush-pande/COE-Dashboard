package com.amdocs.digital.ms.coe.dashboard.componenttests.config;

import com.amdocs.msnext.securityjwt.impl.AutoConfiguration;
import com.amdocs.msnext.securityjwt.impl.AutoConfigurationAddOns;
import com.amdocs.digital.ms.coe.dashboard.gateways.config.AsyncChannelsConfig;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {
   // These two classes are security-related. These classes are ignored.
   AutoConfiguration.class, AutoConfigurationAddOns.class

   // This class subscribes the Kafka subscribers to Kafka; The class is reloaded in the
   // TestConfigForLaptop. In CI, we don't want to subscribe to Kafka (we have a
   // real service deployed for that).
   ,AsyncChannelsConfig.class

   })

public class TestConfig {

}