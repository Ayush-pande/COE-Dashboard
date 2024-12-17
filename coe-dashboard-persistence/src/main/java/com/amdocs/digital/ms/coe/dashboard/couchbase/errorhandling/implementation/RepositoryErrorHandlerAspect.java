package com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.implementation;

import javax.inject.Inject;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.interfaces.IRepositoryInterceptor;

@Aspect
public class RepositoryErrorHandlerAspect{

    @Inject
    protected IRepositoryInterceptor persistenceInterceptor;

    @AfterThrowing(pointcut="execution(* com.amdocs.digital.ms.coe.dashboard.couchbase.DashboardRepository.*(..))", throwing="ex")
    public void handleAllErrors(Exception ex) {
        persistenceInterceptor.handleAllErrors(ex);
    }

}