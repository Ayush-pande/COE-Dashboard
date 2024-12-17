package com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.implementation;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.interfaces.IGatewayInterceptor;

@Aspect
public class GatewayErrorHandlerAspect {

    @Inject
    private IGatewayInterceptor gatewayInterceptor;

    @AfterThrowing(pointcut="execution(* com.amdocs.digital.ms.coe.dashboard.gateways..get*(..))", throwing="ex")
    public void handleGetterErrors(JoinPoint joinPoint, Exception ex) {
        gatewayInterceptor.handleGetterErrors(joinPoint, ex);
    }

    @AfterThrowing(pointcut="execution(* com.amdocs.digital.ms.coe.dashboard.gateways..create*(..))", throwing="ex")
    public void handleCreateErrors(JoinPoint joinPoint, Exception ex) {
        gatewayInterceptor.handleCreateErrors(joinPoint, ex);
    }

}
