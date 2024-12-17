package com.amdocs.digital.ms.coe.dashboard.tests.business.errorhandling.exceptions;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityNotFoundException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EntityNotFoundExceptionTest {

    private static final String ENTITY_NAME = "ENTITY_NAME";
    private static final String ENTITY_KEY = "ENTITY_KEY";

    private EntityNotFoundException throwablEntityNotFoundException;
    private EntityNotFoundException entityNotFoundException;

    @Mock
    private Throwable cause;

    private void createMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() {

        createMocks();
        MessagesTest messages = new MessagesTest();
        throwablEntityNotFoundException = new EntityNotFoundException(ENTITY_NAME, ENTITY_KEY, cause, messages);
        entityNotFoundException = new EntityNotFoundException(ENTITY_NAME, ENTITY_KEY, messages);
    }

    @Test
    public void getMessage() {

        Assert.assertEquals("message", entityNotFoundException.getMessage());
    }

    @Test
    public void getParameters() {

        Assert.assertNotNull(entityNotFoundException.getParameters());
    }

    @Test
    public void getErrorCode() {

        Assert.assertNotNull(entityNotFoundException.getErrorCode());
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }
}
