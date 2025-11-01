package com.CodeWordle.CodeWordle.dto;

import com.CodeWordle.CodeWordle.model.Game;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AttemptResponse {
    private List<LetterFeedback> feedback;
    private Game.GameStatus gameStatus;
    private int attemptsLeft;
    private String correctWord; // Send the correct word only when the game is over
}
