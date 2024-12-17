package com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.implementation;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.AuthenticationException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityKeyAlreadyExistsException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.EntityNotFoundException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.OptimisticLockException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.SystemNotAvailableException;
import com.amdocs.digital.ms.coe.dashboard.business.errorhandling.exceptions.UnknownException;
import com.amdocs.digital.ms.coe.dashboard.business.internationalization.interfaces.IMessages;
import com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.interfaces.IRepositoryInterceptor;
import com.amdocs.msbase.persistence.couchbase.CouchbaseAggregateException;
import com.amdocs.msbase.persistence.couchbase.CouchbaseAggregateException.Cause;
import com.couchbase.client.core.error.AuthenticationFailureException;
import com.couchbase.client.core.error.BucketNotFoundException;
import com.couchbase.client.core.error.CasMismatchException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.core.error.ServiceNotAvailableException;
import com.couchbase.client.core.error.TemporaryFailureException;


public class RepositoryInterceptor implements IRepositoryInterceptor {

    private static final Integer PRIMARY_CAUSE_INDEX = 0;
    private static final String COUCHBASE = "Couchbase";
    private static final String CHECKOUT = "Checkout";

    @Inject
    private IMessages messages;

    /**
     * Maps {@link CouchbaseAggregateException} to an appropriate
     * {@link AbstractApplicationException} based on the cause.
     */
    public void handleAllErrors(Throwable ex) {

        String key = null;

        if (ex instanceof CouchbaseAggregateException aggEx) {
            Cause primaryCause = aggEx.getCauses().get(PRIMARY_CAUSE_INDEX);
            key = primaryCause.getKey();
            Throwable initialError = primaryCause.getError();
            ex = initialError;
        }

        if (ex instanceof BucketNotFoundException || ex instanceof ServiceNotAvailableException) {
            throw new SystemNotAvailableException(COUCHBASE, ex, messages);
        } else if (ex instanceof AuthenticationFailureException) {
            throw new AuthenticationException(ex, messages);
        } else if (ex instanceof DocumentExistsException) {
            throw new EntityKeyAlreadyExistsException(key, "", ex, messages);
        } else if (ex instanceof DocumentNotFoundException) {
            throw new EntityNotFoundException(CHECKOUT, key, ex, messages);
        } else if (ex instanceof CasMismatchException || ex instanceof TemporaryFailureException) {
            throw new OptimisticLockException(key, ex, messages);
        } else {
            throw new UnknownException(ex, messages);
        }
    }
}
