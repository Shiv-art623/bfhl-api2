package com.campus.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response DTO representing the response JSON structure for the BFHL endpoint.
 * Contains all requested campus hiring details and processed results.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BfhlResponse {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @JsonProperty("user_id")
    private String userId;

    private String email;

    @JsonProperty("roll_number")
    private String rollNumber;

    @JsonProperty("odd_numbers")
    private List<String> oddNumbers;

    @JsonProperty("even_numbers")
    private List<String> evenNumbers;

    private List<String> alphabets;

    @JsonProperty("special_characters")
    private List<String> specialCharacters;

    private int sum;

    @JsonProperty("concat_string")
    private String concatString;
}
