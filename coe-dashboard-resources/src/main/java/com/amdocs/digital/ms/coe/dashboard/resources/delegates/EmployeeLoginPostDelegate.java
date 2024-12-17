//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.delegates;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.business.repository.dto.interfaces.IEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.business.repository.dto.interfaces.ILoginResponseDTO;
import com.amdocs.digital.ms.coe.dashboard.business.services.interfaces.IEmployeeLoginPostApplicationService;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.EmployeeApiImpl.EmployeeLoginPostParameters;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.EmployeeApiImpl.IEmployeeLoginPostDelegate;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapEmployeeToEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapLoginResponseDTOToLoginResponse;
import com.amdocs.digital.ms.coe.dashboard.resources.models.Employee;
import com.amdocs.digital.ms.coe.dashboard.resources.models.LoginResponse;
import com.amdocs.msbase.queryparameters.Filter;
import com.amdocs.msbase.queryparameters.PaginationInfo;
import com.amdocs.msbase.queryparameters.SelectedAttributes;
import com.amdocs.msbase.queryparameters.SortFields;
import org.springframework.http.ResponseEntity;

public class EmployeeLoginPostDelegate implements IEmployeeLoginPostDelegate
{
	@Inject
	protected IMapEmployeeToEmployeeDTO mapEmployeeToEmployeeDTO;

	@Inject
	protected IMapLoginResponseDTOToLoginResponse mapLoginResponseDTOToLoginResponse;

	@Inject
	protected IEmployeeLoginPostApplicationService employeeLoginPostApplicationService;

	@Override
	public ResponseEntity<LoginResponse> execute(EmployeeLoginPostParameters parameters)
	{
		Employee employee = parameters.getEmployee();

		IEmployeeDTO requestEmployeeDTO = mapEmployeeToEmployeeDTO.map(employee);
		Filter filterConditions = parameters.getFilterConditions();
		PaginationInfo paginationInfo = parameters.getPaginationInfo();
		SelectedAttributes selectAttributes = parameters.getSelectAttributes();
		SortFields sortFields = parameters.getSortFields();

		ILoginResponseDTO serviceResult = employeeLoginPostApplicationService.employeeLoginPost(requestEmployeeDTO, filterConditions, paginationInfo, selectAttributes, sortFields);
		LoginResponse response = mapLoginResponseDTOToLoginResponse.map(serviceResult);
		return ResponseEntity.ok(response);
	}
}