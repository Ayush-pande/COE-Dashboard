package com.amdocs.digital.ms.coe.dashboard.testpact.pactprovider.testpact.run;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginService {
	
	//TODO extract loginUrl from config.yaml - com.amdocs.msnext.securitya3s.url
	public static final String LOGIN_HOST = "ilde9435.eaas.amdocs.com";
	public static final String lOGIN_PORT = "30081";
	public static final String lOGIN_URL = "http://" + LOGIN_HOST + ":" + lOGIN_PORT + "/asmServices/services/v1.0/auth/tokens";
	
	public static String getLoginToken() {
		
		// set params - headers/body
		HttpHeaders headers = new HttpHeaders();
		Map<String,String> headerMap = new HashMap<String, String>();
		headerMap.put("Authorization", "Basic cHJvZHVjdGNvbmZpZ3VyYXRvci1hcHAtaWQ6VW5peDExIQ==");
		headerMap.put("Content-Type", "application/json");
		headers.setAll(headerMap);
		String loginRequestbody = "{\"auth\":{\"dialects\":[\"A3S_Core_Public\"],\"on-behalf-of\":{\"user-credentials\":{\"username\":\"AdminUser\",\"password\":\"Unix11!\"}}}}";
		
		// invoke Rest
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> loginRequestbodyEnity = new HttpEntity<>(loginRequestbody, headers);
		String loginResponse = restTemplate.postForObject(lOGIN_URL, loginRequestbodyEnity, String.class);
		
		// extract authorization token
		String loginToken = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			loginToken = mapper.readTree(loginResponse).get("subjectToken").asText();
		} catch (IOException e) {
			throw new RuntimeException("Failed to Login!");
		}
		return loginToken;
	}

}
