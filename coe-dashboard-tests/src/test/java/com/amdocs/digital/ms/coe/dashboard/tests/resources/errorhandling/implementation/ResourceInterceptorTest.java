package com.amdocs.digital.ms.coe.dashboard.tests.resources.errorhandling.implementation;

import static org.mockito.Mockito.*;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.AuthenticationException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadGatewayException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadRequestException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ConflictException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityCannotBeUpdatedException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityNotFoundException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ForbiddenException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.SystemNotAvailableException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.implementation.ResourceInterceptor;
import com.amdocs.digital.ms.coe.dashboard.resources.errorhandling.exceptions.WebException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;

@RunWith(MockitoJUnitRunner.class)
public class ResourceInterceptorTest {

    private static final String ERROR_CODE = "ERROR_CODE";
    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    private ResourceInterceptor resourceInterceptor;
    
    @Mock
    private IMessages messages;

    @Mock
    private AuthenticationException authenticationException;
    @Mock
    private BadGatewayException badGatewayException;
    @Mock
    private ConflictException conflictException;
    @Mock
    private EntityCannotBeUpdatedException entityCannotBeUpdatedException;
    @Mock
    private EntityNotFoundException entityNotFoundException;
    @Mock
    private ForbiddenException forbiddenException;
    @Mock
    private BadRequestException badRequestException;
    @Mock
    private SystemNotAvailableException systemNotAvailableException;
    @Mock
    private UnsupportedOperationException unsupportedOperationException;
    @Mock
    private SecurityException securityException;

    private void createMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws Exception {

        createMocks();
        resourceInterceptor = new ResourceInterceptor();

        Field messagesField = resourceInterceptor.getClass().getDeclaredField("messages");
        messagesField.setAccessible(true);
        messagesField.set(resourceInterceptor, messages);
        when(messages.getMessage(anyString())).thenReturn(ERROR_MESSAGE);

        when(authenticationException.getErrorCode()).thenReturn(ERROR_CODE);
        when(authenticationException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(badGatewayException.getParameters().get(BadGatewayException.GATEWAY_STATUS)).thenReturn(null);
        when(conflictException.getErrorCode()).thenReturn(ERROR_CODE);
        when(conflictException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(entityCannotBeUpdatedException.getErrorCode()).thenReturn(ERROR_CODE);
        when(entityCannotBeUpdatedException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(entityNotFoundException.getErrorCode()).thenReturn(ERROR_CODE);
        when(entityNotFoundException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(forbiddenException.getErrorCode()).thenReturn(ERROR_CODE);
        when(forbiddenException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(badRequestException.getErrorCode()).thenReturn(ERROR_CODE);
        when(badRequestException.getMessage()).thenReturn(ERROR_MESSAGE);
        when(systemNotAvailableException.getErrorCode()).thenReturn(ERROR_CODE);
        when(systemNotAvailableException.getMessage()).thenReturn(ERROR_MESSAGE);

        unsupportedOperationException = new UnsupportedOperationException(ERROR_MESSAGE);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsAuthenticationException() {
    	resourceInterceptor.handleAllErrors(authenticationException);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsBadGatewayException() {
    	resourceInterceptor.handleAllErrors(badGatewayException);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsConflictException() {
    	resourceInterceptor.handleAllErrors(conflictException);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsEntityCannotBeUpdatedException() {
    	resourceInterceptor.handleAllErrors(entityCannotBeUpdatedException);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsEntityNotFoundException() {
    	resourceInterceptor.handleAllErrors(entityNotFoundException);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsForbiddenException() {
    	resourceInterceptor.handleAllErrors(forbiddenException);
    }  

    @Test(expected = WebException.class)
    public void handleAllErroreBadRequestException() {
    	resourceInterceptor.handleAllErrors(badRequestException);
    }

    @Test(expected = WebException.class)
    public void handleAllErroreSystemNotAvailableException() {
    	resourceInterceptor.handleAllErrors(systemNotAvailableException);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void handleAllErroreUnsupportedOperationException() {
    	resourceInterceptor.handleAllErrors(unsupportedOperationException);
    }

    @Test(expected = WebException.class)
    public void handleAllErrorsSecurityException() {
    	resourceInterceptor.handleAllErrors(securityException);
    }

}
