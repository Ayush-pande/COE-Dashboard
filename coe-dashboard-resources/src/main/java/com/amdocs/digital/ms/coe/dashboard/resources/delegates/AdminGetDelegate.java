//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.delegates;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.business.services.interfaces.IAdminGetApplicationService;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.AdminApiImpl.AdminGetParameters;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.AdminApiImpl.IAdminGetDelegate;
import com.amdocs.msbase.queryparameters.Filter;
import com.amdocs.msbase.queryparameters.PaginationInfo;
import com.amdocs.msbase.queryparameters.SelectedAttributes;
import com.amdocs.msbase.queryparameters.SortFields;
import org.springframework.http.ResponseEntity;

public class AdminGetDelegate implements IAdminGetDelegate
{
	@Inject
	protected IAdminGetApplicationService adminGetApplicationService;

	@Override
	public ResponseEntity<String> execute(AdminGetParameters parameters)
	{
		Filter filterConditions = parameters.getFilterConditions();
		PaginationInfo paginationInfo = parameters.getPaginationInfo();
		SelectedAttributes selectAttributes = parameters.getSelectAttributes();
		SortFields sortFields = parameters.getSortFields();

		String response = adminGetApplicationService.adminGet(filterConditions, paginationInfo, selectAttributes, sortFields);
		return ResponseEntity.ok(response);
	}
}