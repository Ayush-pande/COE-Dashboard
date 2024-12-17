//This code was generated by MS-Builder
package com.amdocs.digital.ms.coe.dashboard.resources.config;

import org.springframework.context.annotation.Bean; //NOSONAR
import org.springframework.context.annotation.Configuration;

import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapAdminDTOToAdmin;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapAdminToAdminDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapEmployeeDTOToEmployee;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapEmployeeToEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapInlineResponse400DTOToInlineResponse400;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapInlineResponse400ToInlineResponse400DTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapLoginResponseDTOToLoginResponse;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.implementation.MapLoginResponseToLoginResponseDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapAdminDTOToAdmin;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapAdminToAdminDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapEmployeeDTOToEmployee;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapEmployeeToEmployeeDTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapInlineResponse400DTOToInlineResponse400;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapInlineResponse400ToInlineResponse400DTO;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapLoginResponseDTOToLoginResponse;
import com.amdocs.digital.ms.coe.dashboard.resources.mappers.interfaces.IMapLoginResponseToLoginResponseDTO;

/**
 * Mappers configuration file.
 */
@Configuration
public class ResourceMappersConfig {

	@Bean
	public IMapAdminDTOToAdmin mapAdminDTOToAdmin()
	{
		return new MapAdminDTOToAdmin();
	}

	@Bean
	public IMapAdminToAdminDTO mapAdminToAdminDTO()
	{
		return new MapAdminToAdminDTO();
	}

	@Bean
	public IMapEmployeeDTOToEmployee mapEmployeeDTOToEmployee()
	{
		return new MapEmployeeDTOToEmployee();
	}

	@Bean
	public IMapEmployeeToEmployeeDTO mapEmployeeToEmployeeDTO()
	{
		return new MapEmployeeToEmployeeDTO();
	}

	@Bean
	public IMapInlineResponse400DTOToInlineResponse400 mapInlineResponse400DTOToInlineResponse400()
	{
		return new MapInlineResponse400DTOToInlineResponse400();
	}

	@Bean
	public IMapInlineResponse400ToInlineResponse400DTO mapInlineResponse400ToInlineResponse400DTO()
	{
		return new MapInlineResponse400ToInlineResponse400DTO();
	}

	@Bean
	public IMapLoginResponseDTOToLoginResponse mapLoginResponseDTOToLoginResponse()
	{
		return new MapLoginResponseDTOToLoginResponse();
	}

	@Bean
	public IMapLoginResponseToLoginResponseDTO mapLoginResponseToLoginResponseDTO()
	{
		return new MapLoginResponseToLoginResponseDTO();
	}

}