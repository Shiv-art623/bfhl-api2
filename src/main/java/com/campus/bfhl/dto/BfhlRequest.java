package com.campus.bfhl.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request DTO representing the input JSON structure for the BFHL endpoint.
 * Contains field validation to handle null or empty inputs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BfhlRequest {

    @NotNull(message = "Input data list cannot be null")
    @NotEmpty(message = "Input data list cannot be empty")
    private List<String> data;
}
