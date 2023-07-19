package com.example.libraryupdater.model;
import lombok.Getter;

import java.util.List;

@Getter
public class ContentData {
    private String description;
    private String title;
    private String author;
    private String category;
    private List<Long> recommendationIdList;

    public ContentData(String description, String title, String author,
                       String category, List<Long> recommendationIdList) {
        this.author = author;
        this.description = description;
        this.title = title;
        this.category = category;
        this.recommendationIdList = recommendationIdList;
    }
}
