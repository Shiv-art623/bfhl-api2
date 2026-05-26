package com.campus.bfhl.controller;

import com.campus.bfhl.dto.BfhlRequest;
import com.campus.bfhl.dto.BfhlResponse;
import com.campus.bfhl.service.BfhlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class that exposes the /bfhl endpoint.
 * Accepts POST requests, performs validation, and returns HTTP 200 on success.
 */
@Slf4j
@RestController
@RequestMapping("/bfhl")
@RequiredArgsConstructor
public class BfhlController {

    private final BfhlService bfhlService;

    /**
     * Handles incoming POST requests to /bfhl.
     * Uses @Valid to ensure standard request constraints are met.
     *
     * @param request the BfhlRequest containing input data
     * @return ResponseEntity containing the processed BfhlResponse payload
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@Valid @RequestBody BfhlRequest request) {
        log.info("Received POST request for /bfhl with data size: {}",
                request.getData() != null ? request.getData().size() : 0);

        BfhlResponse response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }
}
