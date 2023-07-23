package com.example.libraryupdater.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ContentData {
    private String description;
    private String title;
    private String author;
    private String category;
    private List<Long> recommendationIdList;
}
