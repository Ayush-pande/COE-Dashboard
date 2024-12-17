package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class EntityKeyAlreadyExistsException extends AbstractApplicationException {

	private static final String USER_MSG = "USER_MSG";

    public EntityKeyAlreadyExistsException(final String entityKey, final String userMsg, final Throwable cause,
            final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.ENTITY_KEY_ALREADY_EXISTS_EXCEPTION_MSG, entityKey, userMsg),
                ErrorCodes.ENTITY_KEY_ALREADY_EXISTS_EXCEPTION_MSG,
                initMap(entityKey, userMsg));
    }

	public EntityKeyAlreadyExistsException(final String entityKey, final String userMsg, final IMessages messages) {
		this(entityKey, userMsg, null, messages);
	}

	private static Map<String,Object> initMap(String entityKey, String userMsg){
		Map<String,Object> map = new HashMap<>();
		map.put(EntityNotFoundException.ENTITY_KEY, entityKey);
		map.put(USER_MSG, userMsg);
		return map;
	}
}
