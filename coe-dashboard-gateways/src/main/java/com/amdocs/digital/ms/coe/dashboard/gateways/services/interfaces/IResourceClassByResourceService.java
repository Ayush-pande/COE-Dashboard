package com.amdocs.digital.ms.coe.dashboard.gateways.services.interfaces;

public interface IResourceClassByResourceService {
    
    String getResourceTypeFullyQualifiedName (String resourceName);

    String getMappedResourceTypeFullyQualifiedName (String resourceName);

}
