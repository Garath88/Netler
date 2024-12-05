package com.netler.api.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netler.api.model.LatencyInfo;
import com.netler.api.service.AvgLatencyService;

@RestController
public class LatencyController {
    private final AvgLatencyService avgLatencyService;

    LatencyController(AvgLatencyService avgLatencyService) {
        this.avgLatencyService = avgLatencyService;
    }

    @Cacheable("latencies")
    @GetMapping("/latencies")
    public ResponseEntity<LatencyInfo> getAvgLatencies(
        @RequestParam String startDate,
        @RequestParam String endDate) {
        LatencyInfo latencyInfo = avgLatencyService.getAvgLatencies(startDate, endDate);
        return new ResponseEntity<>(latencyInfo, HttpStatus.OK);
    }
}
