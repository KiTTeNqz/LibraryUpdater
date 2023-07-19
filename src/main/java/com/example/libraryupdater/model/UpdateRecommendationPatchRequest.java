package com.example.libraryupdater.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRecommendationPatchRequest {
    private Long id;
    private List<Long> recommendationIdList;
}
