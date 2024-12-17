package com.amdocs.digital.ms.coe.dashboard.componenttests.tests;

import com.amdocs.digital.ms.coe.dashboard.componenttests.config.TestConfig;
import com.amdocs.msbase.testing.component.framework.ComponentTestBaseClass5;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;

/**
 * There is some extra service-related configuration that we want all our tests
 * to share, so we use this base-class.
 */
@SpringBootTest(classes = { TestConfig.class })
public abstract class BaseTestClass extends ComponentTestBaseClass5 {

    @MockBean
    public AuthenticationManager authenticationManager;

}