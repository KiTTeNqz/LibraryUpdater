package com.example.libraryupdater.model.inner.response;

import com.example.libraryupdater.model.ContentData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExternalBook {
    private Long id;
    private String renter;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime rentalStartTime;
    @JsonFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime rentalStopTime;
    private String title;
    private String publisher;
    private List<Long> recommendationIdList;
    private List<ContentData> contentData;
}