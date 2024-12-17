package com.amdocs.digital.ms.coe.dashboard.gateways.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amdocs.digital.ms.coe.dashboard.gateways.services.implementation.ResourceClassByResourceService;
import com.amdocs.digital.ms.coe.dashboard.gateways.services.interfaces.IResourceClassByResourceService;

@Configuration
public class AsyncMessagingConfig {
    @Bean
    public IResourceClassByResourceService resourceClassByResourceService() {
        return new ResourceClassByResourceService();
    }
}
