package com.amdocs.digital.ms.coe.dashboard.couchbase.config;

import org.aspectj.lang.Aspects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.implementation.RepositoryErrorHandlerAspect;
import com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.implementation.RepositoryInterceptor;
import com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.interfaces.IRepositoryInterceptor;

@Configuration
@EnableAspectJAutoProxy
public class RepositoryErrorHandlerConfig {

    @Bean
    public IRepositoryInterceptor getPersistenceInterceptor(){
        return new RepositoryInterceptor();
    }

    @Bean
    public RepositoryErrorHandlerAspect getPersistenceErrorHandlerAspect(){
        return Aspects.aspectOf(RepositoryErrorHandlerAspect.class);
    }

}
