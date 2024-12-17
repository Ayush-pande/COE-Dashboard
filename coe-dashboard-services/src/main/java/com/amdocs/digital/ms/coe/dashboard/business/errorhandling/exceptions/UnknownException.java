package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class UnknownException extends AbstractApplicationException {

    public UnknownException(Throwable cause, final IMessages messages) {
        super(cause,  messages.getMessage(ErrorCodes.UNKNOWN_EXCEPTION_MSG), ErrorCodes.UNKNOWN_EXCEPTION_MSG);
    }
}
