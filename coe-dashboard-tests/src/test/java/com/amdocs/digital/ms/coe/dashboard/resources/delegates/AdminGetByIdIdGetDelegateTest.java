//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.delegates;


import org.junit.Test;
import javax.inject.Inject;
import com.amdocs.digital.ms.coe.dashboard.tests.setup.TestSetUp;

import com.amdocs.digital.ms.coe.dashboard.resources.implementation.AdminApiImpl.AdminGetByIdIdGetParameters;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.AdminApiImpl.IAdminGetByIdIdGetDelegate;
import com.amdocs.msbase.queryparameters.Filter;
import com.amdocs.msbase.queryparameters.PaginationInfo;
import com.amdocs.msbase.queryparameters.SelectedAttributes;
import com.amdocs.msbase.queryparameters.SortFields;


public class AdminGetByIdIdGetDelegateTest extends TestSetUp
{
    @Inject
    protected IAdminGetByIdIdGetDelegate adminGetByIdIdGetDelegate;

    @Test(expected = UnsupportedOperationException.class)
    public void testExecute()
    {
        AdminGetByIdIdGetParameters adminGetByIdIdGetParameters = createAdminGetByIdIdGetParameters();

        //TODO implement test accordingly
        adminGetByIdIdGetDelegate.execute(adminGetByIdIdGetParameters);
    }
	
    private AdminGetByIdIdGetParameters createAdminGetByIdIdGetParameters(){
        AdminGetByIdIdGetParameters adminGetByIdIdGetParameters = new AdminGetByIdIdGetParameters();
        adminGetByIdIdGetParameters.setAuthorization(getDefaultStringValue());
        adminGetByIdIdGetParameters.setId(getDefaultStringValue());
        return adminGetByIdIdGetParameters;
    }


}