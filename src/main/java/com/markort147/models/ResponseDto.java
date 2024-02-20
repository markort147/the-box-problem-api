package com.markort147.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Represents the response containing the optimal set of item identifiers.
 * This set includes the IDs of items that form the best combination based on the given criteria.
 */
@Setter
@Getter
public class ResponseDto {
    /**
     * The set of item IDs that represent the best combination.
     */
    private Set<Integer> items;
}
