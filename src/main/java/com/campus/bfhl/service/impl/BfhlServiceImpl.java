package com.campus.bfhl.service.impl;

import com.campus.bfhl.dto.BfhlRequest;
import com.campus.bfhl.dto.BfhlResponse;
import com.campus.bfhl.exception.InvalidInputException;
import com.campus.bfhl.service.BfhlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BfhlService containing the core business rules:
 * - Parsing numbers, checking if odd/even and summing them.
 * - Filtering alphabets and capitalizing them.
 * - Categorizing remaining items as special characters.
 * - Reversing alphabets and mapping to alternating capitalization.
 */
@Slf4j
@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.id:shivani_prajapati_26052026}")
    private String userId;

    @Value("${bfhl.user.email:shivani.prajapati@university.edu}")
    private String email;

    @Value("${bfhl.user.roll-number:CU12345678}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        log.info("Processing request data...");

        // Validation for null input request or null/empty data list
        if (request == null || request.getData() == null) {
            log.error("Request or request data list is null");
            throw new InvalidInputException("Input data list cannot be null");
        }

        if (request.getData().isEmpty()) {
            log.error("Request data list is empty");
            throw new InvalidInputException("Input data list cannot be empty");
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        int sum = 0;

        // Iterate and process each element in the input list
        for (String item : request.getData()) {
            if (item == null || item.trim().isEmpty()) {
                log.error("Encountered null or empty element in request data list");
                throw new InvalidInputException("Input data elements cannot be null, empty, or blank");
            }

            // Check if string is a valid integer (supporting positive/negative signs)
            if (item.matches("^-?\\d+$")) {
                try {
                    int val = Integer.parseInt(item);
                    sum += val;
                    if (val % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse number that matched numeric regex: {}", item);
                    // Fallback to special characters if integer range is exceeded
                    specialCharacters.add(item);
                }
            }
            // Check if string contains only alphabetical characters
            else if (item.matches("^[a-zA-Z]+$")) {
                alphabets.add(item.toUpperCase());
            }
            // Everything else is treated as a special character
            else {
                specialCharacters.add(item);
            }
        }

        // Build concat_string: reversed and in alternating caps
        String concatString = generateConcatString(request.getData());

        BfhlResponse response = BfhlResponse.builder()
                .isSuccess(true)
                .userId(userId)
                .email(email)
                .rollNumber(rollNumber)
                .oddNumbers(oddNumbers)
                .evenNumbers(evenNumbers)
                .alphabets(alphabets)
                .specialCharacters(specialCharacters)
                .sum(sum)
                .concatString(concatString)
                .build();

        log.info("Request data processed successfully. Success status: {}", response.isSuccess());
        return response;
    }

    /**
     * Extracts all alphabetical characters from the input list,
     * concatenates them, reverses the string, and formats it to alternating-case
     * starting with uppercase (e.g. "EoDdCbAa").
     */
    private String generateConcatString(List<String> data) {
        StringBuilder alphabetsConcat = new StringBuilder();

        // Concatenate alphabetical strings in order
        for (String item : data) {
            if (item != null && item.matches("^[a-zA-Z]+$")) {
                alphabetsConcat.append(item);
            }
        }

        // Reverse the accumulated characters
        String reversed = alphabetsConcat.reverse().toString();

        // Convert to alternating-case (Index 0 is Upper, Index 1 is Lower, etc.)
        StringBuilder alternatingCaps = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                alternatingCaps.append(Character.toUpperCase(c));
            } else {
                alternatingCaps.append(Character.toLowerCase(c));
            }
        }

        return alternatingCaps.toString();
    }
}
