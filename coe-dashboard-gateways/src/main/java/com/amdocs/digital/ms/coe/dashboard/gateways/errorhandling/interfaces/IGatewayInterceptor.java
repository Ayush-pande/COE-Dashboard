package com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.interfaces;

import org.aspectj.lang.JoinPoint;

public interface IGatewayInterceptor {

    public void handleGetterErrors(JoinPoint joinPoint, Throwable ex);

    public void handleCreateErrors(JoinPoint joinPoint, Throwable ex);

}
