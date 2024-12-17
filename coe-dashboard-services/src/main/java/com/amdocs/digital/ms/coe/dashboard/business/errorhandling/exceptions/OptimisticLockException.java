package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class OptimisticLockException extends AbstractApplicationException {

    public OptimisticLockException(final String entityKey, final Throwable cause, final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.OPTIMISTIC_LOCKING_EXCEPTION_MSG, entityKey),
                ErrorCodes.OPTIMISTIC_LOCKING_EXCEPTION_MSG,
                initMap(entityKey));
    }

	private static Map<String,Object> initMap(String entityKey){
		Map<String,Object> map = new HashMap<>();
		map.put(EntityNotFoundException.ENTITY_KEY, entityKey);
		return map;
	}
}
