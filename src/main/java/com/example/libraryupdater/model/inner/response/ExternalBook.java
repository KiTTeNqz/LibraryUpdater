package com.example.libraryupdater.model.inner.response;

import com.example.libraryupdater.model.ContentData;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExternalBook {
    private Long id;
    private String renter;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime rentalStartTime; //
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime rentalStopTime;
    private String title;
    private String publisher;
    private List<Long> recommendationIdList;
    private List<ContentData> contentData;
}