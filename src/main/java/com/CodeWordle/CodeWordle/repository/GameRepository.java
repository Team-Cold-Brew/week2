package com.CodeWordle.CodeWordle.repository;

import com.CodeWordle.CodeWordle.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GameRepository {

    private final JdbcTemplate jdbcTemplate;

    public Optional<Game> findById(Long id) {
        String sql = "SELECT * FROM games WHERE id = ?";
        try {
            Game game = jdbcTemplate.queryForObject(sql, new GameRowMapper(), id);
            return Optional.ofNullable(game);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Saves a new game and returns the saved game with its generated ID.
     */
    public Game save(Game game) {
        String sql = "INSERT INTO games (word_id, start_time, status, attempts_left) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, game.getWordId());
            ps.setTimestamp(2, Timestamp.valueOf(game.getStartTime()));
            ps.setString(3, game.getStatus().name());
            ps.setInt(4, game.getAttemptsLeft());
            return ps;
        }, keyHolder);

        // Set the generated ID back to the game object
        game.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return game;
    }

    public void update(Game game) {
        String sql = "UPDATE games SET status = ?, attempts_left = ? WHERE id = ?";
        jdbcTemplate.update(sql, game.getStatus().name(), game.getAttemptsLeft(), game.getId());
    }

    private static class GameRowMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Game.builder()
                    .id(rs.getLong("id"))
                    .wordId(rs.getLong("word_id"))
                    .startTime(rs.getTimestamp("start_time").toLocalDateTime())
                    .status(Game.GameStatus.valueOf(rs.getString("status")))
                    .attemptsLeft(rs.getInt("attempts_left"))
                    .build();
        }
    }
}