package com.netler.api.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netler.api.client.RequestInfoClient;
import com.netler.api.model.RequestInfo;

@SpringBootTest
@AutoConfigureMockMvc
class LatencyControllerTest {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static List<RequestInfo> testDataOne;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RequestInfoClient requestInfoClient;

    @BeforeAll
    public static void setUp() {
        try (InputStream in = Thread.currentThread()
            .getContextClassLoader()
            .getResourceAsStream("testData.json")) {
            testDataOne = MAPPER.readValue(in, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Timeout(2)
    void testCalcAvgRespTime() throws Exception {
        String startDate = "2021-01-01";
        String endDate = "2021-01-02";
        String expectedJson = "{\"period\":[\"2021-01-01\",\"2021-01-02\"],\""
            + "averageLatencies\":["
            + "{\"serviceId\":1,\"numberOfRequests\":5,\"averageResponseTimeMs\":336},"
            + "{\"serviceId\":2,\"numberOfRequests\":4,\"averageResponseTimeMs\":286},"
            + "{\"serviceId\":3,\"numberOfRequests\":4,\"averageResponseTimeMs\":326},"
            + "{\"serviceId\":4,\"numberOfRequests\":3,\"averageResponseTimeMs\":330},"
            + "{\"serviceId\":5,\"numberOfRequests\":2,\"averageResponseTimeMs\":460}]}";

        when(requestInfoClient.getRequestInfo(startDate, endDate)).thenReturn(testDataOne);
        mockMvc.perform(get("/latencies?startDate={startDate}&endDate={endDate}", startDate, endDate))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedJson));
        verify(requestInfoClient, times(1)).getRequestInfo(startDate, endDate);
    }

    //TODO: Add new json test data and write tests :)
    /*
    @Test
    @Timeout(2)
    void testDuplicateRequestIds() {
    }

    @Test
    @Timeout(2)
    void testDuplicateRequestAndServiceIds() {
    }

    @Test
    @Timeout(2)
    void testMissingFields() {
    }

    @Test
    @Timeout(2)
    void testEmptyValues() {
    }

    @Test
    @Timeout(2)
    void testUnknownProperties() {
    }
     */

}
