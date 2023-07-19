package com.example.libraryupdater.model.inner.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Recommendation {
    private String publisher;
    private String title;

    public Recommendation(String title){
        this.title = title;
    }
}
