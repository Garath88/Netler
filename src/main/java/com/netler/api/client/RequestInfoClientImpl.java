package com.netler.api.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import com.netler.api.model.RequestInfo;
import com.netler.util.DateRangeParser;

@Component
public final class RequestInfoClientImpl implements RequestInfoClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInfoClientImpl.class);
    private static final String URI_BASE = "http://latencyapi-env.eba-kqb2ph3i.eu-west-1.elasticbeanstalk.com/latencies/";
    private static final RestClient REST_CLIENT = RestClient.create();

    public List<RequestInfo> getRequestInfo(String startDate, String endDate) {
        List<RequestInfo> responseData = new ArrayList<>();
        try {
            List<String> dateRange = DateRangeParser.getDateRange(startDate, endDate);
            for (String date : dateRange) {
                responseData.addAll(getRequestInfo(date));
            }
            if (responseData.isEmpty()) {
                String errorMessage = "No data found for the specified date range: " + startDate + " to " + endDate;
                LOGGER.error(errorMessage);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
            }

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return Collections.unmodifiableList(responseData);
    }

    private Set<RequestInfo> getRequestInfo(String date) {
        try {
            return REST_CLIENT.get()
                .uri(URI_BASE + "?date={date}", date)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        } catch (HttpServerErrorException e) {
            LOGGER.error("API returned 500 for date {}", date);
            return Collections.emptySet();
        } catch (Exception e) {
            LOGGER.error("Unexpected error occurred for date {}: {}", date, e.getMessage());
            throw e; // Rethrow other exceptions
        }
    }
}
