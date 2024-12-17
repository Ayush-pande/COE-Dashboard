package com.amdocs.digital.ms.coe.dashboard.tests.couchbase.errorhandling.implementation;

import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.implementation.RepositoryInterceptor;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.UnknownException;
import com.amdocs.msbase.persistence.couchbase.CouchbaseAggregateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.List;

import static org.mockito.BDDMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryInterceptorTest {

    private static final Integer PRIMARY_CAUSE_INDEX = 0;
    private RepositoryInterceptor repositoryInterceptor;
    private MessagesTest messages;

    @Mock
    private CouchbaseAggregateException couchbaseAggregateException;
    @Mock
    private CouchbaseAggregateException.Cause cause;
    @Mock
    private List<CouchbaseAggregateException.Cause> causes;

    private void createMocks() {

        MockitoAnnotations.initMocks(this);
    }

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {

        createMocks();
        repositoryInterceptor = new RepositoryInterceptor();
        messages = new MessagesTest();
        when(couchbaseAggregateException.getCauses()).thenReturn(causes);
        when(causes.get(PRIMARY_CAUSE_INDEX)).thenReturn(cause);

        Field field = RepositoryInterceptor.class.getDeclaredField("messages");
        field.setAccessible(true);
        field.set(repositoryInterceptor, messages);

    }

    @Test(expected=com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.UnknownException.class)
    public void handleAllErrors() {
        repositoryInterceptor.handleAllErrors(couchbaseAggregateException);
    }

    private class MessagesTest implements IMessages {

        @Override
        public String getMessage(String key, String... params) {

            return "message";
        }
    }
}
