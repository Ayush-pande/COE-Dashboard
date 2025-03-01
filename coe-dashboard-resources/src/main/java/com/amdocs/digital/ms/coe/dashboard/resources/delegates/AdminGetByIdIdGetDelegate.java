//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.delegates;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.business.repository.dto.interfaces.IEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.business.services.interfaces.IAdminGetByIdIdGetApplicationService;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.AdminApiImpl.AdminGetByIdIdGetParameters;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.AdminApiImpl.IAdminGetByIdIdGetDelegate;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapEmployeeDTOToEmployee;
import com.amdocs.digital.ms.coe.dashboard.resources.models.Employee;
import com.amdocs.msbase.queryparameters.Filter;
import com.amdocs.msbase.queryparameters.PaginationInfo;
import com.amdocs.msbase.queryparameters.SelectedAttributes;
import com.amdocs.msbase.queryparameters.SortFields;
import org.springframework.http.ResponseEntity;

public class AdminGetByIdIdGetDelegate implements IAdminGetByIdIdGetDelegate
{
	@Inject
	protected IMapEmployeeDTOToEmployee mapEmployeeDTOToEmployee;

	@Inject
	protected IAdminGetByIdIdGetApplicationService adminGetByIdIdGetApplicationService;

	@Override
	public ResponseEntity<Employee> execute(AdminGetByIdIdGetParameters parameters)
	{
		String authorization = parameters.getAuthorization();
		Filter filterConditions = parameters.getFilterConditions();
		String id = parameters.getId();
		PaginationInfo paginationInfo = parameters.getPaginationInfo();
		SelectedAttributes selectAttributes = parameters.getSelectAttributes();
		SortFields sortFields = parameters.getSortFields();

		IEmployeeDTO serviceResult = adminGetByIdIdGetApplicationService.adminGetByIdIdGet(authorization, filterConditions, id, paginationInfo, selectAttributes, sortFields);
		Employee response = mapEmployeeDTOToEmployee.map(serviceResult);
		return ResponseEntity.ok(response);
	}
}