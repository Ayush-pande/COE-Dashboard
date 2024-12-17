package com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.implementation;

import javax.servlet.http.HttpServletRequest;

import javax.inject.Inject;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ErrorCodes;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.exceptions.WebException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.resources.models.Error;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.amdocs.msbase.resource.handler.traceid.ITraceIdProvider;

/**
 *
 * Maps an {@link AbstractApplicationException} to a {@link Error}
 *
 */
@ControllerAdvice
public class ExceptionToErrorMapper extends ResponseEntityExceptionHandler {

   private static final Logger LOG = LoggerFactory.getLogger(ExceptionToErrorMapper.class);

    @Inject
    protected ITraceIdProvider tracer;

    @Inject
    private IMessages messages;

    /**
     * Maps an exception to a {@link Error}. If the exception is not
     * of type {@link AbstractApplicationException} then the error response will
     * default to Internal Server Error
     *
     * @param req The Request
     * @param exception The exception that is being mapped to an Error
     * @return an Error object
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<Error> handleException(HttpServletRequest req, Exception exception) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error";
        String code = "";
        if( exception instanceof WebException webEx){
            status = webEx.getStatus();
            message = webEx.getMessage();
            code = webEx.getCode();
        }
        if ( exception instanceof AccessDeniedException acsEx) {
            status = HttpStatus.FORBIDDEN;
            message = acsEx.getMessage();
        }
        if ( exception instanceof UnsupportedOperationException unsupportedOperationException) {
            status = HttpStatus.NOT_IMPLEMENTED;
            message = unsupportedOperationException.getMessage();
        }
        Error error = new Error();
        error.setCode(code);
        error.setMessage(message);

        return new ResponseEntity<>(error, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
        HttpStatus status, WebRequest request) {

        Error errorResponse = new Error();
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        Error errorResponse = new Error();
        errorResponse.setCode(ErrorCodes.METHOD_NOT_ALLOWED_EXCEPTION);
        errorResponse.setMessage(messages.getMessage(ErrorCodes.METHOD_NOT_ALLOWED_EXCEPTION));
        LOG.error(ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        Error errorResponse = new Error();
        errorResponse.setCode(ErrorCodes.BAD_REQUEST_FIELD_MSG);

        JsonParser processor = null;

        if (ex.getCause() instanceof JsonParseException jsonParseException) {

            processor = jsonParseException.getProcessor();

        } else if (ex.getCause() instanceof JsonMappingException jsonMappingException) {

            processor = (JsonParser) jsonMappingException.getProcessor();
        }

        String fieldName = getFieldName(processor);
        String message = messages.getMessage(ErrorCodes.BAD_REQUEST_FIELD_MSG, fieldName);

        errorResponse.setMessage(message);

        LOG.error(messages.getMessage(ErrorCodes.BAD_REQUEST_FIELD_MSG, getFieldName(processor)));

        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

    	Error errorResponse = new Error();
        errorResponse.setCode(ErrorCodes.BAD_REQUEST_FIELD_MSG);

        JsonParser processor = null;

        if (ex.getCause() instanceof JsonParseException jsonParseException) {

            processor = jsonParseException.getProcessor();

        } else if (ex.getCause() instanceof JsonMappingException jsonMappingException) {

            processor = (JsonParser) jsonMappingException.getProcessor();
        }

        String fieldName = getFieldName(processor);
        String errMsg = messages.getMessage(errorResponse.getCode(), fieldName);
        errorResponse.setTraceId(tracer.getTraceId());
        errorResponse.setMessage(errMsg);

        LOG.error(errMsg);
        return new ResponseEntity<>(errorResponse, status);
    }

    private String getFieldName(JsonParser processor) {
        if (processor != null) {
            try {
                return processor.getCurrentName();
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return null;
    }
}