package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.HashMap;
import java.util.Map;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;


public class BadRequestException extends AbstractApplicationException {
    private static final String HEADER_FIELD_NAME = "HEADER_FIELD_NAME";
    private static final long serialVersionUID = 1L;

    public BadRequestException(final String headerFieldName, final Throwable cause, final IMessages messages) {
        super(cause, messages.getMessage
                (headerFieldName == null ? ErrorCodes.BAD_REQUEST_EXCEPTION_MSG : ErrorCodes.BAD_REQUEST_EXCEPTION_FIELD_MSG,
                headerFieldName), headerFieldName == null ?
                ErrorCodes.BAD_REQUEST_EXCEPTION_MSG : ErrorCodes.BAD_REQUEST_EXCEPTION_FIELD_MSG,
                initMap(headerFieldName));
    }


    public BadRequestException(final String headerFieldName, final  IMessages messages) {
        this(headerFieldName, null, messages);
    }

    private static Map<String, Object> initMap(final String headerFieldName) {
        final Map<String, Object> ret = new HashMap<>(1);
        ret.put(HEADER_FIELD_NAME, headerFieldName);
        return ret;
    }
}
