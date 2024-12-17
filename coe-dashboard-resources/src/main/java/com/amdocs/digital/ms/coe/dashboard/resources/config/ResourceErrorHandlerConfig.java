package com.amdocs.digital.ms.coe.dashboard.resources.config;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.implementation.ResourceErrorHandlerAspect;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.implementation.ResourceInterceptor;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.interfaces.IResourceInterceptor;

@Configuration
public class ResourceErrorHandlerConfig {

    @Bean
    public IResourceInterceptor getResourceInterceptor() {
        return new ResourceInterceptor();
    }

    @Bean
    public ResourceErrorHandlerAspect getResourceErrorHandlerAspect() {
        return Aspects.aspectOf(ResourceErrorHandlerAspect.class);
    }

}
