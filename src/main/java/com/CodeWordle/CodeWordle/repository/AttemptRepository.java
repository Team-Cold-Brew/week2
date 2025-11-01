package com.CodeWordle.CodeWordle.repository;

import com.CodeWordle.CodeWordle.model.Attempt;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AttemptRepository {

    private final JdbcTemplate jdbcTemplate;

    public void save(Attempt attempt) {
        String sql = "INSERT INTO attempts (game_id, guessed_word, attempt_time) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql,
                attempt.getGameId(),
                attempt.getGuessedWord(),
                Timestamp.valueOf(attempt.getAttemptTime()));
    }

    public List<Attempt> findByGameId(Long gameId) {
        String sql = "SELECT * FROM attempts WHERE game_id = ? ORDER BY attempt_time ASC";
        return jdbcTemplate.query(sql, new AttemptRowMapper(), gameId);
    }

    private static class AttemptRowMapper implements RowMapper<Attempt> {
        @Override
        public Attempt mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Attempt.builder()
                    .id(rs.getLong("id"))
                    .gameId(rs.getLong("game_id"))
                    .guessedWord(rs.getString("guessed_word"))
                    .attemptTime(rs.getTimestamp("attempt_time").toLocalDateTime())
                    .build();
        }
    }
}