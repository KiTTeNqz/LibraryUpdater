package com.example.libraryupdater.model;

import com.example.libraryupdater.model.inner.response.ExternalBook;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateRecommendationGetExternalResponse {
    private List<ExternalBook> bookData;
}
