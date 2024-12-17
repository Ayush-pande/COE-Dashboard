package com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.implementation;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;
import org.springframework.http.HttpStatus;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import com.amdocs.msbase.api.IAmdocsExternalClientException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadGatewayException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.interfaces.IGatewayInterceptor;

public class GatewayInterceptor implements IGatewayInterceptor {

    @Inject
    private IMessages messages;

    @Override
    public void handleGetterErrors(JoinPoint joinPoint, Throwable ex) {
        String serviceName = joinPoint.getSignature().getName().replace("get", "");
        feignExceptionToErrorMapper(ex, serviceName);
        throw new BadGatewayException(serviceName, null, "", null, ex, messages);
    }

    @Override
    public void handleCreateErrors(JoinPoint joinPoint, Throwable ex) {
        String serviceName = joinPoint.getSignature().getName().replace("create", "");
        feignExceptionToErrorMapper(ex, serviceName);
        throw new BadGatewayException(serviceName, null, "", null, ex, messages);

    }


    private void feignExceptionToErrorMapper(Throwable ex, String serviceName) {
        Throwable cause = ex;
        if (ex instanceof HystrixRuntimeException){
            cause = ex.getCause();
        }

        /**
        If you need some special custom handling for a specific exception type, implement it here:
        if (cause instanceof ShoppingCartExternalClientException feignEx)
        {

            throw new ....
        }
        */

        // This should handle all ClientException classes generated from swagger files.
        if (cause instanceof IAmdocsExternalClientException aec) {
            String errorMessage = aec.getExternalMessageText();
            String errorCode = aec.getErrorCode();
            HttpStatus httpStatus = HttpStatus.valueOf(aec.getHttpStatusCode());

            throw new BadGatewayException(serviceName, errorCode, errorMessage, httpStatus, cause, messages);
        }
    }
}
