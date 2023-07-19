package com.example.libraryupdater.model;

import com.example.libraryupdater.model.inner.request.SearchAttribute;

import java.util.ArrayList;
import java.util.List;

public class UpdateRecommendationExternalRequest {
    private List<SearchAttribute> searchAttributes;
    public UpdateRecommendationExternalRequest() {
        this.searchAttributes = new ArrayList<>();
    }

    public List<SearchAttribute> getSearchAttributes() {
        return searchAttributes;
    }

    public UpdateRecommendationExternalRequest(List<SearchAttribute> searchAttributes) {
        this.searchAttributes = searchAttributes;
    }
}
