package com.amdocs.digital.ms.coe.dashboard.tests.gateways.errorhandling.implementation;

import static org.mockito.Mockito.*;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.gateways.errorhandling.implementation.GatewayInterceptor;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadGatewayException;
import com.amdocs.msbase.api.IAmdocsExternalClientException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.apache.http.HttpStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class GatewayInterceptorTest {

    private static final String GET_SERVICE_NAME = "getSERVICE_NAME";
    private static final String CREATE_SERVICE_NAME = "createSERVICE_NAME";
    @Mock
    Signature signature;
    private GatewayInterceptor gatewayInterceptor;
    private MessagesTest messagesTest;
    private AmdocsExternalClientException amdocsExternalClientException;
    @Mock
    private JoinPoint joinPointWithGet;
    @Mock
    private JoinPoint joinPointWithCreate;
    @Mock
    private HystrixRuntimeException hystrixRuntimeException;
    @Mock
    private Throwable cause;

    private void createMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        createMocks();
        gatewayInterceptor = new GatewayInterceptor();
        messagesTest = new MessagesTest();
        amdocsExternalClientException = new AmdocsExternalClientException();
        Field messages = gatewayInterceptor.getClass().getDeclaredField("messages");
        messages.setAccessible(true);
        messages.set(gatewayInterceptor, messagesTest);
        when(joinPointWithGet.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(GET_SERVICE_NAME);
        when(joinPointWithCreate.getSignature()).thenReturn(signature);
        when(signature.getName()).thenReturn(CREATE_SERVICE_NAME);
        when(hystrixRuntimeException.getCause()).thenReturn(cause);

    }

    @Test(expected = BadGatewayException.class)
    public void handleGetterErrorsHystrixRuntimeException() {
        gatewayInterceptor.handleGetterErrors(joinPointWithGet, hystrixRuntimeException);
    }
    
    @Test(expected = BadGatewayException.class)
    public void handleGetterErrorsAmdocsExternalClientException() {
        gatewayInterceptor.handleGetterErrors(joinPointWithGet, amdocsExternalClientException);
    }

    @Test(expected = BadGatewayException.class)
    public void handleCreateErrorsHystrixRuntimeException() {
        gatewayInterceptor.handleCreateErrors(joinPointWithCreate, hystrixRuntimeException);
    }

    @Test(expected = BadGatewayException.class)
    public void handleCreateErrorsAmdocsExternalClientException() {
        gatewayInterceptor.handleCreateErrors(joinPointWithCreate, amdocsExternalClientException);
    }

    private static class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }

    private static class AmdocsExternalClientException extends Throwable implements IAmdocsExternalClientException {
    
        private static final long serialVersionUID = 1L;
        
        @Override
        public int getHttpStatusCode() {

            return HttpStatus.SC_GATEWAY_TIMEOUT;
        }

        @Override
        public String getExternalMessageText() {

            return "ExternalMessageText";
        }

        @Override
        public String getErrorCode() {

            return "ErrorCode";
        }
    }

}
