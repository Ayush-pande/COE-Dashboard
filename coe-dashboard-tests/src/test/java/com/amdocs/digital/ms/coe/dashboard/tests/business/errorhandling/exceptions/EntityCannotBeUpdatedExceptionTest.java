package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityCannotBeUpdatedException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EntityCannotBeUpdatedExceptionTest {

    private static final String ENTITY_NAME = "ENTITY_NAME";
    private static final String ENTITY_KEY = "ENTITY_KEY";

    private EntityCannotBeUpdatedException entityCannotBeUpdatedException;
    private EntityCannotBeUpdatedException entityCannotBeUpdatedExceptionNoThrowable;
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
        entityCannotBeUpdatedException = new EntityCannotBeUpdatedException(ENTITY_NAME, ENTITY_KEY, cause, messages);
        entityCannotBeUpdatedExceptionNoThrowable = new EntityCannotBeUpdatedException(ENTITY_NAME, ENTITY_KEY, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", entityCannotBeUpdatedException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(entityCannotBeUpdatedException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(entityCannotBeUpdatedException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }
}
