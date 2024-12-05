package com.netler.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ServiceInfo {
    @JsonProperty("serviceId")
    private Long serviceId;
    @JsonProperty("numberOfRequests")
    private Integer numberOfRequests;
    @JsonProperty("averageResponseTimeMs")
    private Long averageResonseTimeMs;

    public ServiceInfo(Long serviceId, Integer numberOfRequests, Long averageResonseTimeMs) {
        this.serviceId = serviceId;
        this.numberOfRequests = numberOfRequests;
        this.averageResonseTimeMs = averageResonseTimeMs;
    }
}
