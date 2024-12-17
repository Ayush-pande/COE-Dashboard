package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.OptimisticLockException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OptimisticLockExceptionTest {

    private static final String ENTITY_KEY = "ENTITY_KEY";

    private OptimisticLockException optimisticLockException;
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
        optimisticLockException = new OptimisticLockException(ENTITY_KEY, cause, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", optimisticLockException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(optimisticLockException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(optimisticLockException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }

}
