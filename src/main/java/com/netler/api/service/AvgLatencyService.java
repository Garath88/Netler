package com.netler.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.netler.api.client.RequestInfoClient;
import com.netler.api.model.LatencyInfo;
import com.netler.api.model.RequestInfo;
import com.netler.api.model.ServiceInfo;

@Service
public class AvgLatencyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvgLatencyService.class);
    private final RequestInfoClient requestInfoClient;

    AvgLatencyService(RequestInfoClient requestInfoClient) {
        this.requestInfoClient = requestInfoClient;
    }

    public LatencyInfo getAvgLatencies(String startDate, String endDate) {
        LOGGER.info("Fetching request info from client");
        List<RequestInfo> requestInfo = requestInfoClient.getRequestInfo(startDate, endDate);
        Map<String, RequestInfo> uniqueRequestInfo = removeDuplicateValues(requestInfo);
        List<ServiceInfo> avgLatency = calculateAvgLatencyByService(uniqueRequestInfo);
        return new LatencyInfo(List.of(startDate, endDate), avgLatency);
    }

    private Map<String, RequestInfo> removeDuplicateValues(List<RequestInfo> responseData) {
        return responseData.stream()
            .collect(Collectors.toMap(key -> key.getRequestId() + " - " + key.getServiceId(), // Request ID with Service ID as key
                key -> key, (old, latest) -> latest, // Keep latest found value
                HashMap::new
            ));
    }

    private List<ServiceInfo> calculateAvgLatencyByService(Map<String, RequestInfo> uniqueReuqestInfo) {
        return uniqueReuqestInfo.values().stream()
            .collect(Collectors.groupingBy(RequestInfo::getServiceId)) // Group by serviceId
            .entrySet().stream()
            .map(entry -> {
                long serviceId = entry.getKey();
                List<RequestInfo> requests = entry.getValue();
                double average = requests.stream()
                    .mapToLong(RequestInfo::getMilliSecondsDelay)
                    .average()
                    .getAsDouble();
                return new ServiceInfo(serviceId, requests.size(), (long)average);
            })
            .toList();
    }
}
