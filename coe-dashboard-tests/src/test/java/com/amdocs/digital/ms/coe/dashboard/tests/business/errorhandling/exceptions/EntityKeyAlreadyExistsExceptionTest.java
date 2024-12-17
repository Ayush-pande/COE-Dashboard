package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityKeyAlreadyExistsException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EntityKeyAlreadyExistsExceptionTest {

    private static final String USER_MSG = "USER_MSG";
    private static final String ENTITY_KEY = "ENTITY_KEY";

    private EntityKeyAlreadyExistsException entityKeyAlreadyExistsException;
    private EntityKeyAlreadyExistsException entityKeyAlreadyExistsExceptionNoThrowable;
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
        entityKeyAlreadyExistsException = new EntityKeyAlreadyExistsException(ENTITY_KEY, USER_MSG, cause, messages);
        entityKeyAlreadyExistsExceptionNoThrowable = new EntityKeyAlreadyExistsException(ENTITY_KEY, USER_MSG, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", entityKeyAlreadyExistsException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(entityKeyAlreadyExistsException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(entityKeyAlreadyExistsException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }
}
