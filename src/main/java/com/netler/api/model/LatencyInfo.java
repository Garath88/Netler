package com.netler.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class LatencyInfo {
    @JsonProperty("period")
    private List<String> period;
    @JsonProperty("averageLatencies")
    private List<ServiceInfo> serviceInfo;

    public LatencyInfo(List<String> period, List<ServiceInfo> serviceInfo) {
        this.period = period;
        this.serviceInfo = serviceInfo;
    }
}
