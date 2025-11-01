package com.CodeWordle.CodeWordle.controller;


import com.CodeWordle.CodeWordle.dto.AttemptRequest;
import com.CodeWordle.CodeWordle.dto.AttemptResponse;
import com.CodeWordle.CodeWordle.model.Game;
import com.CodeWordle.CodeWordle.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller to handle game logic via API endpoints.
 */
@RestController
@RequestMapping("/api/games") // Base path for all game-related API calls
@RequiredArgsConstructor
public class GameApiController {

    private final GameService gameService;

    /**
     * Endpoint to start a new game.
     * @param topicId The ID of the topic selected by the user.
     * @return The newly created game session.
     */
    @PostMapping("/start")
    public ResponseEntity<?> startNewGame(@RequestParam Long topicId) {
        try {
            Game newGame = gameService.startNewGame(topicId);
            return ResponseEntity.ok(newGame);
        } catch (IllegalStateException e) {
            // This happens if no words are found for the topic
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint to process a user's attempt.
     * @param gameId The ID of the current game.
     * @param request The request body containing the guessed word.
     * @return A response with feedback on the attempt and the current game state.
     */
    @PostMapping("/{gameId}/attempts")
    public ResponseEntity<?> processAttempt(@PathVariable Long gameId, @RequestBody AttemptRequest request) {
        try {
            AttemptResponse response = gameService.processAttempt(gameId, request.getWord());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // Catches errors like "Game not found", "Game is over", "Incorrect word length"
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
