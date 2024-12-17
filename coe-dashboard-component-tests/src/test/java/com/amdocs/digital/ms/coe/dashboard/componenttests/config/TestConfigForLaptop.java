package com.amdocs.digital.ms.coe.dashboard.componenttests.config;

import javax.inject.Inject;
import com.amdocs.digital.ms.coe.dashboard.gateways.config.AsyncChannelsConfig;


import com.amdocs.msbase.testing.component.config.ConfigForLaptop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

@Configuration
@ConfigForLaptop
// We are importing the configuration for subscribing to Kafka; This code will load
// the real Kafka delegates.
@Import({ AsyncChannelsConfig.class })
public class TestConfigForLaptop {

}