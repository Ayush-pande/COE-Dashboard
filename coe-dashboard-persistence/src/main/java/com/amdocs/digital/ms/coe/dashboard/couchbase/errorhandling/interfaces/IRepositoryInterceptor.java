package com.amdocs.digital.ms.coe.dashboard.couchbase.errorhandling.interfaces;

public interface IRepositoryInterceptor {

    public void handleAllErrors(Throwable ex);

}
