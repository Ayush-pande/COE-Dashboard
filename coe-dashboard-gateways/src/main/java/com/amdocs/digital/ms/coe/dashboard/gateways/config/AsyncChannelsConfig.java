package com.amdocs.digital.ms.coe.dashboard.gateways.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.amdocs.digital.ms.coe.dashboard.gateways.topics.IDashboardPublish;
import com.amdocs.digital.ms.coe.dashboard.gateways.topics.IDashboardResourcesPublish;
import com.amdocs.msb.asyncmessaging.config.MsbAsyncMessagingConfig;

@Configuration
@ConditionalOnProperty("spring.cloud.stream.enabled")
@Import({MsbAsyncMessagingConfig.class})
@EnableBinding({IDashboardPublish.class, IDashboardResourcesPublish.class})
@SuppressWarnings("java:S1874")
public class AsyncChannelsConfig {
}
