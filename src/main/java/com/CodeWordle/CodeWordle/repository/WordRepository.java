package com.CodeWordle.CodeWordle.repository;


import com.CodeWordle.CodeWordle.model.Word;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Finds a random word for a given topic ID.
     * H2's RAND() function is used for random selection.
     */
    public Optional<Word> findRandomWordByTopicId(Long topicId) {
        String sql = "SELECT * FROM words WHERE topic_id = ? ORDER BY RAND() LIMIT 1";
        try {
            Word word = jdbcTemplate.queryForObject(sql, new WordRowMapper(), topicId);
            return Optional.ofNullable(word);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Word> findById(Long id) {
        String sql = "SELECT * FROM words WHERE id = ?";
        try {
            Word word = jdbcTemplate.queryForObject(sql, new WordRowMapper(), id);
            return Optional.ofNullable(word);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static class WordRowMapper implements RowMapper<Word> {
        @Override
        public Word mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Word.builder()
                    .id(rs.getLong("id"))
                    .wordText(rs.getString("word_text"))
                    .topicId(rs.getLong("topic_id"))
                    .build();
        }
    }
}