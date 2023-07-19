package com.example.libraryupdater.mapper;

import com.example.libraryupdater.model.UpdateRecommendationAdapterRequest;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.inner.request.Recommendation;
import com.example.libraryupdater.model.inner.request.SearchAttribute;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecommendationMapper {

    public UpdateRecommendationExternalRequest mapId(UpdateRecommendationAdapterRequest updateRequest) {
        List<SearchAttribute> searchAttributes = new ArrayList<>();
        searchAttributes.add(new SearchAttribute("publisher", updateRequest.getPublisher(), SearchAttribute.Type.EQUAL));
        searchAttributes.add(new SearchAttribute("title", updateRequest.getTitle(), SearchAttribute.Type.EQUAL));
        return new UpdateRecommendationExternalRequest(searchAttributes);
    }

    public UpdateRecommendationExternalRequest mapRecommendation(Recommendation recommendation){
        List<SearchAttribute> searchAttributes = new ArrayList<>();
        searchAttributes.add(new SearchAttribute("title", recommendation.getTitle(), SearchAttribute.Type.EQUAL));
        if(recommendation.getPublisher()==null){
            searchAttributes.add(new SearchAttribute("publisher", "", SearchAttribute.Type.NOT_EMPTY));
        }
        else searchAttributes.add(new SearchAttribute("publisher", recommendation.getPublisher(), SearchAttribute.Type.EQUAL));
        return new UpdateRecommendationExternalRequest(searchAttributes);
    }
}
