//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.delegates;

import javax.inject.Inject;

import com.amdocs.digital.ms.coe.dashboard.business.repository.dto.interfaces.IEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.business.services.interfaces.IEmployeeRegisterPostApplicationService;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.EmployeeApiImpl.EmployeeRegisterPostParameters;
import com.amdocs.digital.ms.coe.dashboard.resources.implementation.EmployeeApiImpl.IEmployeeRegisterPostDelegate;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapEmployeeToEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.models.Employee;
import com.amdocs.msbase.queryparameters.Filter;
import com.amdocs.msbase.queryparameters.PaginationInfo;
import com.amdocs.msbase.queryparameters.SelectedAttributes;
import com.amdocs.msbase.queryparameters.SortFields;
import org.springframework.http.ResponseEntity;

public class EmployeeRegisterPostDelegate implements IEmployeeRegisterPostDelegate
{
	@Inject
	protected IMapEmployeeToEmployeeDTO mapEmployeeToEmployeeDTO;

	@Inject
	protected IEmployeeRegisterPostApplicationService employeeRegisterPostApplicationService;

	@Override
	public ResponseEntity<String> execute(EmployeeRegisterPostParameters parameters)
	{
		Employee employee = parameters.getEmployee();

		IEmployeeDTO requestEmployeeDTO = mapEmployeeToEmployeeDTO.map(employee);
		Filter filterConditions = parameters.getFilterConditions();
		PaginationInfo paginationInfo = parameters.getPaginationInfo();
		SelectedAttributes selectAttributes = parameters.getSelectAttributes();
		SortFields sortFields = parameters.getSortFields();

		String response = employeeRegisterPostApplicationService.employeeRegisterPost(requestEmployeeDTO, filterConditions, paginationInfo, selectAttributes, sortFields);
		return ResponseEntity.ok(response);
	}
}