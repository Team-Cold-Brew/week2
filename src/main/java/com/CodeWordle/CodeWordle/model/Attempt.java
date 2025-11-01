package com.CodeWordle.CodeWordle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a single guess/attempt made by the user in a game.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attempt {

    private Long id;
    private Long gameId;
    private String guessedWord;
    private LocalDateTime attemptTime;

}