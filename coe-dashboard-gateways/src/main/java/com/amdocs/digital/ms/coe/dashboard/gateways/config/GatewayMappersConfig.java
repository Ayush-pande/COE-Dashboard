package com.amdocs.digital.ms.coe.dashboard.gateways.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amdocs.digital.ms.coe.dashboard.gateways.mappers.implementation.ResourceMappersGenericFactory;
import com.amdocs.digital.ms.coe.dashboard.gateways.mappers.interfaces.IMapResource;
import com.amdocs.digital.ms.coe.dashboard.gateways.mappers.implementation.MapResource;
import com.amdocs.msb.asyncmessaging.generics.utils.interfaces.IBaseGenericBeansFactory;

@Configuration
public class GatewayMappersConfig {

    @Bean
    public <T> IMapResource<T> mapResource() {
        return new MapResource<>();
    }

    @Bean
    public IBaseGenericBeansFactory<IMapResource<Object>> resourcesMappersGenericFactory() {
        return new ResourceMappersGenericFactory();
    }


}
