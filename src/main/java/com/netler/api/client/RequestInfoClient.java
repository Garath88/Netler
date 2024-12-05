package com.netler.api.client;

import java.util.List;

import com.netler.api.model.RequestInfo;

public interface RequestInfoClient {
    List<RequestInfo> getRequestInfo(String startDate, String endDate);
}
