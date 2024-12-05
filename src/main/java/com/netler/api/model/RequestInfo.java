package com.netler.api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class RequestInfo {
    private Long requestId;
    private Long serviceId;
    private String date;
    private Long milliSecondsDelay;

    @JsonCreator
    public RequestInfo(
        @JsonProperty(value = "requestId", required = true) Long requestId,
        @JsonProperty(value = "serviceId", required = true) Long serviceId,
        @JsonProperty(value = "date", required = true) String date,
        @JsonProperty(value = "milliSecondsDelay", required = true) Long milliSecondsDelay
    ) {
        this.requestId = requestId;
        this.serviceId = serviceId;
        this.date = date;
        this.milliSecondsDelay = milliSecondsDelay;
    }

    public Long getRequestId() {
        return requestId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getMilliSecondsDelay() {
        return milliSecondsDelay;
    }

    @Override
    public String toString() {
        return "RequestInformation{" +
            "requestId=" + requestId +
            ", serviceId=" + serviceId +
            ", date='" + date + '\'' +
            ", milliSecondsDelay=" + milliSecondsDelay +
            '}';
    }
}
