package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.SystemNotAvailableException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SystemNotAvailableExceptionTest {

    private static final String SYSTEM_NAME = "SYSTEM_NAME";

    private SystemNotAvailableException systemNotAvailableException;
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
        systemNotAvailableException = new SystemNotAvailableException(SYSTEM_NAME, cause, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", systemNotAvailableException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(systemNotAvailableException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(systemNotAvailableException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }
}
