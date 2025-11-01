package com.CodeWordle.CodeWordle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a topic or category for the words.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Topic {

    private Long id;
    private String name;

}