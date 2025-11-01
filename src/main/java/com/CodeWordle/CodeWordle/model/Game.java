package com.CodeWordle.CodeWordle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Represents a single game session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private Long id;
    private Long wordId;
    private LocalDateTime startTime;
    private GameStatus status;
    private int attemptsLeft;

    /**
     * Enum to represent the state of the game.
     */
    public enum GameStatus {
        IN_PROGRESS,
        WON,
        LOST
    }
}