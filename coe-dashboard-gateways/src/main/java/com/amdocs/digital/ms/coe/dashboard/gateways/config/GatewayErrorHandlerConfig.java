package com.amdocs.digital.ms.coe.dashboard.gateways.config;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.implementation.GatewayErrorHandlerAspect;
import com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.implementation.GatewayInterceptor;
import com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.interfaces.IGatewayInterceptor;

@Configuration
@EnableAspectJAutoProxy
public class GatewayErrorHandlerConfig {

    @Bean
    public IGatewayInterceptor getGatewayInterceptor() {
        return new GatewayInterceptor();
    }

    @Bean
    public GatewayErrorHandlerAspect getGatewayErrorHandlerAspect() {
        return Aspects.aspectOf(GatewayErrorHandlerAspect.class);
    }

}
