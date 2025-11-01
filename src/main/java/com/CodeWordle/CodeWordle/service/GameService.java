package com.CodeWordle.CodeWordle.service;

import com.CodeWordle.CodeWordle.dto.AttemptResponse;
import com.CodeWordle.CodeWordle.dto.LetterFeedback;
import com.CodeWordle.CodeWordle.model.Attempt;
import com.CodeWordle.CodeWordle.model.Game;
import com.CodeWordle.CodeWordle.model.Topic;
import com.CodeWordle.CodeWordle.model.Word;
import com.CodeWordle.CodeWordle.repository.AttemptRepository;
import com.CodeWordle.CodeWordle.repository.GameRepository;
import com.CodeWordle.CodeWordle.repository.TopicRepository;
import com.CodeWordle.CodeWordle.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final WordRepository wordRepository;
    private final AttemptRepository attemptRepository;
    private final TopicRepository topicRepository;

    private static final int MAX_ATTEMPTS = 6;

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Transactional
    public Game startNewGame(Long topicId) {
        // Find a random word for the given topic
        Word wordToGuess = wordRepository.findRandomWordByTopicId(topicId)
                .orElseThrow(() -> new IllegalStateException("No words found for the selected topic."));

        // Create a new game instance
        Game newGame = Game.builder()
                .wordId(wordToGuess.getId())
                .startTime(LocalDateTime.now())
                .status(Game.GameStatus.IN_PROGRESS)
                .attemptsLeft(MAX_ATTEMPTS)
                .build();

        // Save the new game and return it
        return gameRepository.save(newGame);
    }

    @Transactional
    public AttemptResponse processAttempt(Long gameId, String guessedWord) {
        // 1. Retrieve the current game and the word to guess
        Game currentGame = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found with ID: " + gameId));
        Word correctWordObj = wordRepository.findById(currentGame.getWordId())
                .orElseThrow(() -> new IllegalStateException("Correct word not found for the game."));
        String correctWord = correctWordObj.getWordText().toUpperCase();
        String upperGuessedWord = guessedWord.toUpperCase();

        // 2. Validate the attempt
        if (currentGame.getStatus() != Game.GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("This game is already over.");
        }
        if (upperGuessedWord.length() != correctWord.length()) {
            throw new IllegalArgumentException("Guessed word has an incorrect length.");
        }

        // 3. Save the attempt
        Attempt attempt = Attempt.builder()
                .gameId(gameId)
                .guessedWord(upperGuessedWord)
                .attemptTime(LocalDateTime.now())
                .build();
        attemptRepository.save(attempt);

        // 4. Generate feedback and update game state
        currentGame.setAttemptsLeft(currentGame.getAttemptsLeft() - 1);
        List<LetterFeedback> feedback = generateFeedback(upperGuessedWord, correctWord);

        // 5. Check for win/loss conditions
        boolean isWin = upperGuessedWord.equals(correctWord);
        if (isWin) {
            currentGame.setStatus(Game.GameStatus.WON);
        } else if (currentGame.getAttemptsLeft() <= 0) {
            currentGame.setStatus(Game.GameStatus.LOST);
        }

        // 6. Update the game in the database
        gameRepository.update(currentGame);

        // 7. Build and return the response
        return AttemptResponse.builder()
                .feedback(feedback)
                .gameStatus(currentGame.getStatus())
                .attemptsLeft(currentGame.getAttemptsLeft())
                .correctWord(currentGame.getStatus() != Game.GameStatus.IN_PROGRESS ? correctWord : null)
                .build();
    }

    /**
     * Compares the guessed word with the correct word and generates feedback.
     */
    private List<LetterFeedback> generateFeedback(String guessedWord, String correctWord) {
        List<LetterFeedback> feedbackList = new ArrayList<>();
        Map<Character, Integer> correctWordCharCounts = new HashMap<>();
        char[] guessedChars = guessedWord.toCharArray();
        char[] correctChars = correctWord.toCharArray();

        // First pass: Mark correct positions (greens)
        for (int i = 0; i < correctWord.length(); i++) {
            if (guessedChars[i] == correctChars[i]) {
                feedbackList.add(new LetterFeedback(guessedChars[i], LetterFeedback.LetterStatus.CORRECT_POSITION));
            } else {
                feedbackList.add(null); // Placeholder for second pass
                correctWordCharCounts.merge(correctChars[i], 1, Integer::sum);
            }
        }

        // Second pass: Mark wrong positions and not in word (yellows and grays)
        for (int i = 0; i < guessedWord.length(); i++) {
            if (feedbackList.get(i) == null) { // If not already marked as green
                char guessedChar = guessedChars[i];
                if (correctWordCharCounts.getOrDefault(guessedChar, 0) > 0) {
                    feedbackList.set(i, new LetterFeedback(guessedChar, LetterFeedback.LetterStatus.WRONG_POSITION));
                    correctWordCharCounts.put(guessedChar, correctWordCharCounts.get(guessedChar) - 1);
                } else {
                    feedbackList.set(i, new LetterFeedback(guessedChar, LetterFeedback.LetterStatus.NOT_IN_WORD));
                }
            }
        }
        return feedbackList;
    }
}