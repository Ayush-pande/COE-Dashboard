package com.amdocs.digital.ms.coe.dashboard.tests.resources.errorhandling.implementation;

import brave.Span;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.exceptions.WebException;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.implementation.ExceptionToErrorMapper;
import com.amdocs.digital.ms.coe.dashboard.resources.models.Error;
import com.amdocs.msbase.resource.handler.traceid.ITraceIdProvider;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.BDDMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExceptionToErrorMapperTest {

    private final String WEB_EXCEPTION_CODE = "40001002";
    private final String WEB_EXCEPTION_MSG = "Request failed with bad request field: {0}.";
    private final HttpStatus WEB_EXCEPTION_STATUS = HttpStatus.BAD_REQUEST;
    private final String ACCESS_DENIED_EXCEPTION_MSG = "Forbidden - {0}";
    private final HttpStatus ACCESS_DENIED_EXCEPTION_STATUS = HttpStatus.FORBIDDEN;
    private final String UNSUPPORTED_EXCEPTION_MSG = "unsupported exception message";
    private final HttpStatus UNSUPPORTED_EXCEPTION_STATUS = HttpStatus.NOT_IMPLEMENTED;

    private WebException webException;
    private AccessDeniedException accessDeniedException;
    private UnsupportedOperationException unsupportedOperationException;
    private ExceptionToErrorMapperExposed exceptionToErrorMapperExposed;
    private ExceptionToErrorMapper exceptionToErrorMapper;
    private MessagesTest messages = new MessagesTest();
    private TracerTest tracer = new TracerTest();
    private Error errorWebEx;
    private Error errorAccessDeniedEx;
    private Error errorUnsupportedEx;

    @Mock
    private JsonParseException jsonParseException;
    @Mock
    private JsonParser processor;
    @Mock
    private HttpServletRequest httpServletRequest;
    @Mock
    private Object body;
    @Mock
    private HttpHeaders headers;
    @Mock
    private WebRequest request;
    @Mock
    private HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException;
    @Mock
    private HttpMessageNotReadableException httpMessageNotReadableException;
    @Mock
    private TypeMismatchException typeMismatchException;

    @Before
    public void setUp() throws IOException, IllegalAccessException, NoSuchFieldException {

        exceptionToErrorMapper = new ExceptionToErrorMapper();
        exceptionToErrorMapperExposed = new ExceptionToErrorMapperExposed();
        webException = new WebException(WEB_EXCEPTION_STATUS, WEB_EXCEPTION_CODE, WEB_EXCEPTION_MSG);
        accessDeniedException = new AccessDeniedException(ACCESS_DENIED_EXCEPTION_MSG);
        unsupportedOperationException = new UnsupportedOperationException(UNSUPPORTED_EXCEPTION_MSG);
        typeMismatchException = new TypeMismatchException(new PropertyChangeEvent("source", "Name", "oldValue", "newValue"), String.class);
        processor = new JsonFactory().createParser("content");
        jsonParseException = new JsonParseException(processor, "jsonParseException");
        Field messagesField = ExceptionToErrorMapper.class.getDeclaredField("messages");
        Field tracerField = ExceptionToErrorMapper.class.getDeclaredField("tracer");
        messagesField.setAccessible(true);
        tracerField.setAccessible(true);
        messagesField.set(exceptionToErrorMapperExposed, messages);
        tracerField.set(exceptionToErrorMapperExposed, tracer);
        processor.overrideCurrentName("Request failed with bad request field: {0}.");
        httpRequestMethodNotSupportedException = new HttpRequestMethodNotSupportedException("httpRequestMethodNotSupportedException");
        when(httpMessageNotReadableException.getCause()).thenReturn(jsonParseException);
        errorWebEx= new Error();
        errorWebEx.code(WEB_EXCEPTION_CODE).setMessage(WEB_EXCEPTION_MSG);
        errorAccessDeniedEx= new Error();
        errorAccessDeniedEx.code("").setMessage(ACCESS_DENIED_EXCEPTION_MSG);
        errorUnsupportedEx= new Error();
        errorUnsupportedEx.code("").setMessage(UNSUPPORTED_EXCEPTION_MSG);

    }

    @Test
    public void handleException() {

        ResponseEntity<Error> webExpectedResponse = new ResponseEntity<>(errorWebEx, WEB_EXCEPTION_STATUS);
        ResponseEntity<Error> accessDeniedExpectedResponse = new ResponseEntity<>(errorAccessDeniedEx, ACCESS_DENIED_EXCEPTION_STATUS);
        ResponseEntity<Error> unsupportedOperationExpectedResponse = new ResponseEntity<>(errorUnsupportedEx, UNSUPPORTED_EXCEPTION_STATUS);

        ResponseEntity<Error> webActualResponse = exceptionToErrorMapper
                .handleException(httpServletRequest, webException);
        ResponseEntity<Error> accessDeniedActualResponse = exceptionToErrorMapper
                .handleException(httpServletRequest, accessDeniedException);
        ResponseEntity<Error> unsupportedOperationActualResponse = exceptionToErrorMapper
                .handleException(httpServletRequest, unsupportedOperationException);

        Assert.assertEquals("webException not as expected", webExpectedResponse.getStatusCodeValue(), webActualResponse.getStatusCodeValue());
        Assert.assertEquals("accessDeniedExceptionResponse not as expected", accessDeniedExpectedResponse.getStatusCodeValue(), accessDeniedActualResponse.getStatusCodeValue()
        );
        Assert.assertEquals("unsupportedOperationExceptionResponse not as expected", unsupportedOperationExpectedResponse.getStatusCodeValue(), unsupportedOperationActualResponse.getStatusCodeValue()
        );

    }

    @Test
    public void handleExceptionInternal() {

        Assert.assertNotNull(exceptionToErrorMapperExposed.handleExceptionInternal(webException, body, headers,
                WEB_EXCEPTION_STATUS, request));
    }

    @Test
    public void handleHttpRequestMethodNotSupported() {

        Assert.assertNotNull(exceptionToErrorMapperExposed.handleHttpRequestMethodNotSupported(
                httpRequestMethodNotSupportedException, headers, WEB_EXCEPTION_STATUS, request));
    }

    @Test
    public void handleHttpMessageNotReadable() {

        ResponseEntity actualResponseEntityParseEx = exceptionToErrorMapperExposed
                .handleHttpMessageNotReadable(httpMessageNotReadableException, headers, HttpStatus.I_AM_A_TEAPOT,
                        request);
        ResponseEntity actualResponseEntityMappingEx = exceptionToErrorMapperExposed
                .handleHttpMessageNotReadable(httpMessageNotReadableException, headers, HttpStatus.I_AM_A_TEAPOT,
                        request);
        Assert.assertEquals("message", ((Error) actualResponseEntityParseEx.getBody()).getMessage());
        Assert.assertEquals("message", ((Error) actualResponseEntityMappingEx.getBody()).getMessage());
    }

    @Test
    public void handleTypeMismatch() {

        ResponseEntity actualResponseEntityParseEx = exceptionToErrorMapperExposed
                .handleTypeMismatch(typeMismatchException, headers, HttpStatus.I_AM_A_TEAPOT,
                        request);
        ResponseEntity actualResponseEntityMappingEx = exceptionToErrorMapperExposed
                .handleTypeMismatch(typeMismatchException, headers, HttpStatus.I_AM_A_TEAPOT,
                        request);
        Assert.assertEquals("message", ((Error) actualResponseEntityParseEx.getBody()).getMessage());
        Assert.assertEquals("message", ((Error) actualResponseEntityMappingEx.getBody()).getMessage());

    }

    public class ExceptionToErrorMapperExposed extends ExceptionToErrorMapper {

        @Override
        public ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                              HttpStatus status, WebRequest request) {

            return super.handleExceptionInternal(ex, body, headers, status, request);
        }

        @Override
        public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {

            return super.handleHttpRequestMethodNotSupported(ex, headers, status, request);
        }

        @Override
        public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                   HttpHeaders headers, HttpStatus status, WebRequest request) {

            return super.handleHttpMessageNotReadable(ex, headers, status, request);
        }

        @Override
        public ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                         HttpStatus status, WebRequest request) {

            return super.handleTypeMismatch(ex, headers, status, request);
        }
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }

    private class TracerTest implements ITraceIdProvider {

        @Override
        public String getTraceId() {

            return "traceId";
        }

        @Override
        public long getTraceIdLong() {

            return 0;
        }

        @Override
        public Span getCurrentSpan() {

            return null;
        }

        @Override
        public Span newTrace() {

            return null;
        }

        @Override
        public void withSpanInScope(Span span) {

        }
    }

}
