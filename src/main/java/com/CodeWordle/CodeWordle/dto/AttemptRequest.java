package com.CodeWordle.CodeWordle.dto;

import lombok.Data;

/**
 * DTO for receiving the user's guess from the request body.
 */
@Data
public class AttemptRequest {
    private String word;
}