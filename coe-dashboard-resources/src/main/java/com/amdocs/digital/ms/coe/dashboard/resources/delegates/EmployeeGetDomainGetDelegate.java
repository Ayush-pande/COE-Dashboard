//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.delegates;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.business.services.interfaces.IEmployeeGetDomainGetApplicationService;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.EmployeeApiImpl.EmployeeGetDomainGetParameters;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.EmployeeApiImpl.IEmployeeGetDomainGetDelegate;
import com.amdocs.msbase.queryparameters.Filter;
import com.amdocs.msbase.queryparameters.PaginationInfo;
import com.amdocs.msbase.queryparameters.SelectedAttributes;
import com.amdocs.msbase.queryparameters.SortFields;
import java.util.List;
import org.springframework.http.ResponseEntity;

public class EmployeeGetDomainGetDelegate implements IEmployeeGetDomainGetDelegate
{
	@Inject
	protected IEmployeeGetDomainGetApplicationService employeeGetDomainGetApplicationService;

	@Override
	public ResponseEntity<List<String>> execute(EmployeeGetDomainGetParameters parameters)
	{
		Filter filterConditions = parameters.getFilterConditions();
		PaginationInfo paginationInfo = parameters.getPaginationInfo();
		SelectedAttributes selectAttributes = parameters.getSelectAttributes();
		SortFields sortFields = parameters.getSortFields();

		List<String> response = employeeGetDomainGetApplicationService.employeeGetDomainGet(filterConditions, paginationInfo, selectAttributes, sortFields);
		return ResponseEntity.ok(response);
	}
}