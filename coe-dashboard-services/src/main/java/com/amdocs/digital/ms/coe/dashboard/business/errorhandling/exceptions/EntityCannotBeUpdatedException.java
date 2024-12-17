package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;

@SuppressWarnings("serial")
public class EntityCannotBeUpdatedException extends AbstractApplicationException {

	public static final String ENTITY_NAME = "ENTITY_NAME";

    public static final String ENTITY_KEY = "ENTITY_KEY";

    public EntityCannotBeUpdatedException(final String entityName, final String entityKey, final Throwable cause,
            final IMessages messages) {
        super(cause, messages.getMessage(ErrorCodes.ENTITY_CANNOT_BE_UPDATED_EXCEPTION_MSG,
                entityName, entityKey), ErrorCodes.ENTITY_CANNOT_BE_UPDATED_EXCEPTION_MSG,
                initMap(entityName, entityKey));
    }

    public EntityCannotBeUpdatedException(final String entityName, final String entityKey, final IMessages messages) {
        this(entityName, entityKey, null, messages);
    }

    private static Map<String, Object> initMap(final String entityName, final String entityKey) {
        final Map<String, Object> ret = new HashMap<>();
        ret.put(EntityNotFoundException.ENTITY_KEY, entityKey);
        ret.put(EntityNotFoundException.ENTITY_NAME, entityName);
        return ret;
    }
}
