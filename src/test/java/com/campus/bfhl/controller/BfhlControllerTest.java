package com.campus.bfhl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.campus.bfhl.dto.BfhlRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for BfhlController.
 * Uses MockMvc to test REST endpoints, JSON serialization, validation,
 * and the response status + payload format.
 */
@SpringBootTest
@AutoConfigureMockMvc
class BfhlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Test successful endpoint execution with standard sample data.
     */
    @Test
    void testProcessData_Success() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success", is(true)))
                .andExpect(jsonPath("$.user_id", is("shivani_prajapati_26052026")))
                .andExpect(jsonPath("$.odd_numbers", contains("1")))
                .andExpect(jsonPath("$.even_numbers", containsInAnyOrder("334", "4")))
                .andExpect(jsonPath("$.alphabets", contains("A", "R")))
                .andExpect(jsonPath("$.special_characters", contains("$")))
                .andExpect(jsonPath("$.sum", is(339)))
                .andExpect(jsonPath("$.concat_string", is("Ra")));
    }

    /**
     * Test endpoint behavior when the data list is empty.
     * Expects HTTP 400 (Bad Request).
     */
    @Test
    void testProcessData_ValidationError_EmptyData() throws Exception {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.error_message", containsString("Validation failed")));
    }

    /**
     * Test endpoint behavior when the request payload contains null or whitespace elements.
     * Expects HTTP 400 (Bad Request) thrown by the service layer.
     */
    @Test
    void testProcessData_BusinessError_BlankElements() throws Exception {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", " ", "1"));

        mockMvc.perform(post("/bfhl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.is_success", is(false)))
                .andExpect(jsonPath("$.error_message", containsString("cannot be null, empty, or blank")));
    }
}
