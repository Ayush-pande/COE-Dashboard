package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class ForbiddenException extends AbstractApplicationException {

    private static final String ERR_MSG = "ERR_MSG";

    public ForbiddenException(final String errMsg, final Throwable cause, final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.FORBIDDEN_EXCEPTION_MSG, errMsg), ErrorCodes.FORBIDDEN_EXCEPTION_MSG, initMap(errMsg));
    }

	private static Map<String, Object> initMap(final String errMsg) {
		final Map<String, Object> ret = new HashMap<>();
		ret.put(ERR_MSG, errMsg);
		return ret;
	}
}
