package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ForbiddenException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ForbiddenExceptionTest {

    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    private ForbiddenException forbiddenException;
    private MessagesTest messages;

    @Mock
    private Throwable cause;

    private void createMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() {

        createMocks();
        messages = new MessagesTest();
        forbiddenException = new ForbiddenException(ERROR_MESSAGE, cause, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", forbiddenException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(forbiddenException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(forbiddenException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }

}
