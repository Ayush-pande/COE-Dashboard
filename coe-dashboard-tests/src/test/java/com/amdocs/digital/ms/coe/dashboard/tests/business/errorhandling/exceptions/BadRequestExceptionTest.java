package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;


import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.BadRequestException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BadRequestExceptionTest {

    private static final String HEADER_FIELD_NAME = "HEADER_FIELD_NAME";

    private BadRequestException badRequestException;
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
        badRequestException = new BadRequestException(HEADER_FIELD_NAME, cause, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", badRequestException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(badRequestException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(badRequestException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }
}
