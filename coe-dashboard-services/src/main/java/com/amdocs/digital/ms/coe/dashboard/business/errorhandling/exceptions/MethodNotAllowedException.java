package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class MethodNotAllowedException extends AbstractApplicationException {

    public MethodNotAllowedException(Throwable cause, final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.METHOD_NOT_ALLOWED_EXCEPTION), ErrorCodes.METHOD_NOT_ALLOWED_EXCEPTION);
    }
}