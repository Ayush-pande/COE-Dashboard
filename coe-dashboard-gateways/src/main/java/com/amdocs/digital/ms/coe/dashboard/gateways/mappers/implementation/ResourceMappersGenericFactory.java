package com.amdocs.digital.ms.coe.dashboard.gateways.mappers.implementation;

import java.util.Map;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.gateways.mappers.interfaces.IMapResource;
import com.amdocs.msb.asyncmessaging.generics.utils.implementation.BaseGenericBeansFactory;

public class ResourceMappersGenericFactory extends BaseGenericBeansFactory<IMapResource<Object>> {

    @Inject
    private Map<String, IMapResource<Object>> mappers;


    @Override
    protected Map<String, IMapResource<Object>> getBeans() {
        return mappers;
    }
}
