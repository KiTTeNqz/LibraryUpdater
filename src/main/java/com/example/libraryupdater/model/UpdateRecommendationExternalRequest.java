package com.example.libraryupdater.model;

import com.example.libraryupdater.model.inner.request.SearchAttribute;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateRecommendationExternalRequest {
    private List<SearchAttribute> searchAttributes;

    public UpdateRecommendationExternalRequest() {
        this.searchAttributes = new ArrayList<>();
    }

    public UpdateRecommendationExternalRequest(List<SearchAttribute> searchAttributes) {
        this.searchAttributes = searchAttributes;
    }
}
