package com.CodeWordle.CodeWordle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a word to be guessed, associated with a topic.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private Long id;
    private String wordText;
    private Long topicId;

}