package com.netler.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.netler.api.controller.LatencyController;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private LatencyController latencyController;

    @Test
    void contextLoads() {
        // to ensure that controller is getting created inside the application context
        assertNotNull(latencyController);
    }
}
