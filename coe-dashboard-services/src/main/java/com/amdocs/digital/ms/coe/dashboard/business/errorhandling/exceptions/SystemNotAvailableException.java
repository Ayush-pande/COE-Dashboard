package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class SystemNotAvailableException extends AbstractApplicationException {

	public static final String SYSTEM_NAME = "SYSTEM_NAME";

    public SystemNotAvailableException(final String systemName, final Throwable cause, final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.SYSTEM_NOT_AVAILABLE_EXCEPTION_MSG, systemName),
                ErrorCodes.SYSTEM_NOT_AVAILABLE_EXCEPTION_MSG,
                initMap(systemName));
    }

	private static Map<String,Object> initMap(String systemName){
		Map<String,Object> map = new HashMap<>();
		map.put(SYSTEM_NAME, systemName);
		return map;
	}
}
