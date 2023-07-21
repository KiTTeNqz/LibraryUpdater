package com.example.libraryupdater.model.inner.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchAttribute {
    private String searchAttribute;
    private String value;
    private Type searchType;

    public SearchAttribute(String searchAttribute, String value, Type searchType) {
        this.searchAttribute = searchAttribute;
        this.value = value;
        this.searchType = searchType;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Type{
        CONTAIN,
        EQUAL,
        NOT_EMPTY,
        BETWEEN
    }

}


