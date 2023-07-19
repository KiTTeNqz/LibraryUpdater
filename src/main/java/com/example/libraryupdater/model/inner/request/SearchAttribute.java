package com.example.libraryupdater.model.inner.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchAttribute {
    private String attribute;
    private String value;
    private Type type;

    @JsonCreator
    public SearchAttribute(
            @JsonProperty("searchAttribute") String attribute,
            @JsonProperty("value") String value,
            @JsonProperty("searchType") Type type) {
        this.attribute = attribute;
        this.value = value;
        this.type = type;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public enum Type{
        CONTAIN,
        EQUAL,
        NOT_EMPTY,
        BETWEEN
    }

}


