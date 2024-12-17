package com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.implementation;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.AbstractApplicationException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.AuthenticationException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadGatewayException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadRequestException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ConflictException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityCannotBeUpdatedException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityKeyAlreadyExistsException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityNotFoundException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ForbiddenException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.OptimisticLockException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.SystemNotAvailableException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.UnknownException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ErrorCodes;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.exceptions.WebException;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.interfaces.IResourceInterceptor;

public class ResourceInterceptor implements IResourceInterceptor {

    @Inject
    protected IMessages messages;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceInterceptor.class);

    /**
     * Maps all non-mapped exceptions and errors into an {@link WebException}.
     * Maps all {@link AbstractApplicationException} into  an {@link WebException}
     * with appropriate HttpStatus.
     */
    @SuppressWarnings({"squid:S3776", "squid:S134", "squid:MethodCyclomaticComplexity"}) // The code is clearer as is
    @Override
    public void handleAllErrors(Throwable ex){

        LOGGER.error(ex.getMessage(), ex);

        if(ex instanceof AbstractApplicationException appEx){

            if(appEx instanceof AuthenticationException) {
                throw new WebException(HttpStatus.UNAUTHORIZED, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof BadGatewayException) {
                //Parsing gateway error code (if present) into an appropriate HttpStatus.
                //If error code is not present or cannot be parsed then default to status BAD_GATEWAY.
                HttpStatus status;
                try{
                    status = (HttpStatus) appEx.getParameters().get(BadGatewayException.GATEWAY_STATUS);
                    if(status == null){
                        status = HttpStatus.BAD_GATEWAY;
                    }
                } catch( Exception e){ // NOSONAR e should not be propagated for security if nothing else
                    throw new WebException(HttpStatus.BAD_GATEWAY, appEx.getErrorCode(), appEx.getMessage());
                }
                throw new WebException(status, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof ConflictException || appEx instanceof EntityKeyAlreadyExistsException
                || appEx instanceof OptimisticLockException) {
                throw new WebException(HttpStatus.CONFLICT, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof EntityCannotBeUpdatedException) {
                throw new WebException(HttpStatus.PRECONDITION_FAILED, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof EntityNotFoundException) {
                throw new WebException(HttpStatus.NOT_FOUND, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof ForbiddenException) {
                throw new WebException(HttpStatus.FORBIDDEN, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof BadRequestException) {
                throw new WebException(HttpStatus.BAD_REQUEST, appEx.getErrorCode(), appEx.getMessage());
            }

            else if( appEx instanceof SystemNotAvailableException || appEx instanceof UnknownException) {
                throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, appEx.getErrorCode(), appEx.getMessage());
            }

            //Unmapped AbstractApplicationExceptions will get the default HttpStatus.
            throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, appEx.getErrorCode(), appEx.getMessage());

        } else {
            if(ex instanceof UnsupportedOperationException unsupportedOperationException)
            {
                throw unsupportedOperationException;
            }
            if(! (ex instanceof WebException) ) {
                throw new WebException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.UNKNOWN_EXCEPTION_MSG,
                    messages.getMessage(ErrorCodes.UNKNOWN_EXCEPTION_MSG));

            }
        }
    }

}