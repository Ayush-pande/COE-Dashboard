package com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions;

import java.util.Map;
import java.util.Collections;

@SuppressWarnings("serial")
public abstract class AbstractApplicationException extends RuntimeException {

    private final Throwable initialCause;
    private final String message;
    private final transient Map<String, Object> parameters;
    private final String messageId;

    protected AbstractApplicationException(final Throwable cause, String message, String messageId){
        this(cause, message, messageId, Collections.emptyMap());
    }

    protected AbstractApplicationException(final Throwable cause, String message, String messageId, Map<String, Object> parameters){
        super(cause);
        this.initialCause = initialCause(cause);
        this.message = message;
        this.messageId = messageId;
        this.parameters = parameters;
    }

    private Throwable initialCause(final Throwable cause) {
        Throwable retCause = null;
        if (cause != null) {
            if (cause instanceof AbstractApplicationException abstractApplicationException) {
                retCause = abstractApplicationException.getInitialCause();
            }
            if (retCause == null) {
                retCause = cause;
            }
        }
        return retCause;
    }

    public Throwable getInitialCause() {
        return initialCause;
    }

    @Override
    public String getMessage()  {
        return message;
    }

    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public String getErrorCode() { return this.messageId; }
}
