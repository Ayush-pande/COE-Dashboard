package com.amdocs.digital.ms.coe.dashboard.gateways.topics;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

import com.amdocs.msb.asyncmessaging.publish.ITopicPublish;

public interface IDashboardPublish extends ITopicPublish {

    @Output("Dashboard_publish")
    @SuppressWarnings("java:S1874")
    SubscribableChannel publish();
}
