package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;


import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.ConflictException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ConflictExceptionTest {

    private static final String ERROR_MESSAGE = "ERROR_MESSAGE";

    private ConflictException conflictException;
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
        conflictException = new ConflictException(ERROR_MESSAGE, cause, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", conflictException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(conflictException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(conflictException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }

}
