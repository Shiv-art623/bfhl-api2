package com.campus.bfhl.service;

import com.campus.bfhl.dto.BfhlRequest;
import com.campus.bfhl.dto.BfhlResponse;

/**
 * Service interface outlining the signature for processing incoming campus hiring requests.
 */
public interface BfhlService {

    /**
     * Process list of inputs to filter numbers (odd/even), alphabets (uppercase),
     * special characters, calculate sum, and build the custom reversed alternating-case string.
     *
     * @param request the BfhlRequest containing input data list
     * @return the BfhlResponse containing processed lists and statistics
     */
    BfhlResponse processData(BfhlRequest request);
}
