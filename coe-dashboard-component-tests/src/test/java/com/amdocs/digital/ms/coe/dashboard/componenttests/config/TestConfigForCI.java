package com.amdocs.digital.ms.coe.dashboard.componenttests.config;


import com.amdocs.msb.asyncmessaging.delegate.IDelegate;
import com.amdocs.msbase.testing.component.config.ConfigForCI;
import com.amdocs.msbase.testing.component.delegatewithhttp.DelegateWithHttpProducer;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import com.amdocs.digital.ms.coe.dashboard.ck.resources.interfaces.AdminApi;
import com.amdocs.digital.ms.coe.dashboard.ck.resources.interfaces.EmployeeApi;

@Configuration
@ConfigForCI
public class TestConfigForCI {

    @Inject
    private DelegateWithHttpProducer delegateProducer;

    @PostConstruct
    public void makeDelegatesWithHttp() {

		Class<?> clientKitInterfaceAdminApi = AdminApi.class;
		delegateProducer.makeAndRegisterDelegatesWithHttp(clientKitInterfaceAdminApi);
		Class<?> clientKitInterfaceEmployeeApi = EmployeeApi.class;
		delegateProducer.makeAndRegisterDelegatesWithHttp(clientKitInterfaceEmployeeApi);
    }
}