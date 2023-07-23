package com.example.libraryupdater.mapper;

import com.example.libraryupdater.model.UpdateRecommendationAdapterRequest;
import com.example.libraryupdater.model.UpdateRecommendationExternalRequest;
import com.example.libraryupdater.model.inner.request.Recommendation;
import com.example.libraryupdater.model.inner.request.SearchAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RecommendationMapperTest {

    @InjectMocks
    private RecommendationMapper recommendationMapper;

    @BeforeEach
    public void setUp() {
        // Инициализируем заглушки
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMapId() {
        // Создаем тестовые данные
        UpdateRecommendationAdapterRequest updateRequest = new UpdateRecommendationAdapterRequest("Publisher", "Title", null);

        // Вызываем метод, который мы хотим протестировать
        UpdateRecommendationExternalRequest result = recommendationMapper.mapId(updateRequest);

        // Проверяем результат
        List<SearchAttribute> searchAttributes = result.getSearchAttributes();
        assertEquals(2, searchAttributes.size());
        assertEquals("publisher", searchAttributes.get(0).getSearchAttribute());
        assertEquals("Publisher", searchAttributes.get(0).getValue());
        assertEquals(SearchAttribute.Type.EQUAL, searchAttributes.get(0).getSearchType());
        assertEquals("title", searchAttributes.get(1).getSearchAttribute());
        assertEquals("Title", searchAttributes.get(1).getValue());
        assertEquals(SearchAttribute.Type.EQUAL, searchAttributes.get(1).getSearchType());
    }

    @Test
    public void testMapRecommendationWithPublisher() {
        // Создаем тестовые данные
        Recommendation recommendation = new Recommendation("Publisher","Title");

        // Вызываем метод, который мы хотим протестировать
        UpdateRecommendationExternalRequest result = recommendationMapper.mapRecommendation(recommendation);

        // Проверяем результат
        List<SearchAttribute> searchAttributes = result.getSearchAttributes();
        assertEquals(2, searchAttributes.size());
        assertEquals("title", searchAttributes.get(0).getSearchAttribute());
        assertEquals("Title", searchAttributes.get(0).getValue());
        assertEquals(SearchAttribute.Type.EQUAL, searchAttributes.get(0).getSearchType());
        assertEquals("publisher", searchAttributes.get(1).getSearchAttribute());
        assertEquals("Publisher", searchAttributes.get(1).getValue());
        assertEquals(SearchAttribute.Type.EQUAL, searchAttributes.get(1).getSearchType());
    }

    @Test
    public void testMapRecommendationWithoutPublisher() {
        // Создаем тестовые данные без publisher
        Recommendation recommendation = new Recommendation("Title");

        // Вызываем метод, который мы хотим протестировать
        UpdateRecommendationExternalRequest result = recommendationMapper.mapRecommendation(recommendation);

        // Проверяем результат
        List<SearchAttribute> searchAttributes = result.getSearchAttributes();
        assertEquals(1, searchAttributes.size());
        assertEquals("title", searchAttributes.get(0).getSearchAttribute());
        assertEquals("Title", searchAttributes.get(0).getValue());
        assertEquals(SearchAttribute.Type.EQUAL, searchAttributes.get(0).getSearchType());
    }
}
