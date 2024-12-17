package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;


import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class AuthenticationException extends AbstractApplicationException{

    public AuthenticationException(final Throwable cause, final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.AUTHENTICATION_EXCEPTION_MSG), ErrorCodes.AUTHENTICATION_EXCEPTION_MSG);
    }

}
