package com.CodeWordle.CodeWordle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LetterFeedback {
    private char letter;
    private LetterStatus status;

    public enum LetterStatus {
        CORRECT_POSITION, // Green
        WRONG_POSITION,   // Yellow
        NOT_IN_WORD       // Gray
    }
}