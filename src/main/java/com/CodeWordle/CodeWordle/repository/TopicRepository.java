package com.CodeWordle.CodeWordle.repository;

import com.CodeWordle.CodeWordle.model.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor // Lombok annotation for constructor injection
public class TopicRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Topic> findAll() {
        String sql = "SELECT * FROM topics";
        return jdbcTemplate.query(sql, new TopicRowMapper());
    }

    /**
     * Inner class to map a ResultSet row to a Topic object.
     */
    private static class TopicRowMapper implements RowMapper<Topic> {
        @Override
        public Topic mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Topic.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }
}