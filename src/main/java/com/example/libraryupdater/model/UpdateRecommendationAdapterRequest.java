package com.example.libraryupdater.model;

import com.example.libraryupdater.model.inner.request.Recommendation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateRecommendationAdapterRequest {
    private String publisher;
    private String title;
    private List<Recommendation> recommendationList;

}
