package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.EntityData;
import com.a1.disasterresponse.service.WikidataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    private WikidataService wikidataService;

    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        wikidataService = Mockito.mock(WikidataService.class);

        categoryController = new CategoryController(wikidataService);
    }

    @Test
    void testIdOf() throws IOException {
        //Mock wikimedia api call
        when(wikidataService.searchForWikidataEntity("blanket")).thenReturn("Q5852");

        // Call api
        ResponseEntity<String> resp = categoryController.idOf("blanket");

        // Verify the response
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("Q5852", resp.getBody());

        verify(wikidataService, times(1)).searchForWikidataEntity("blanket");
    }

    @Test
    void testGetCategoriesOf_nocats() throws IOException {
        EntityData blanketData = new EntityData(
                "Q5852",
                Map.of("en", "blanket", "tr", "battaniye"),
                List.of());

        when(wikidataService.getWikidataEntityFromId("Q5852")).thenReturn(blanketData);


        ResponseEntity<List<EntityData>> resp = categoryController.getCategoriesOf("Q5852");

        // Verify the response
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(0, resp.getBody().size());

        verify(wikidataService, times(1)).getWikidataEntityFromId(anyString());
    }

    @Test
    void testGetCategoriesOf_one_cat() throws IOException {
        EntityData blanketData = new EntityData(
                "Q5852",
                Map.of("en", "blanket", "tr", "battaniye"),
                List.of("Q31808206"));

        EntityData coveringData = new EntityData(
                "Q31808206",
                Map.of("en", "covering"),
                List.of("Q31807746"));

        when(wikidataService.getWikidataEntityFromId("Q5852")).thenReturn(blanketData);
        when(wikidataService.getWikidataEntityFromId("Q31808206")).thenReturn(coveringData);


        ResponseEntity<List<EntityData>> resp = categoryController.getCategoriesOf("Q5852");

        // Verify the response
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(List.of(coveringData), resp.getBody());

        verify(wikidataService, times(2)).getWikidataEntityFromId(anyString());
    }


    @Test
    void testGetCategoriesOf_two_cats() throws IOException {
        EntityData blanketData = new EntityData(
                "Q5852",
                Map.of("en", "blanket", "tr", "battaniye"),
                List.of("Q31808206", "Q31807746"));

        EntityData coveringData = new EntityData(
                "Q31808206",
                Map.of("en", "covering"),
                List.of());

        EntityData furnishingData = new EntityData(
                "Q31807746",
                Map.of("en", "furnishing"),
                List.of("Q10273457", "Q17537576", "Q8205328"));


        when(wikidataService.getWikidataEntityFromId("Q5852")).thenReturn(blanketData);
        when(wikidataService.getWikidataEntityFromId("Q31808206")).thenReturn(coveringData);
        when(wikidataService.getWikidataEntityFromId("Q31807746")).thenReturn(furnishingData);

        ResponseEntity<List<EntityData>> resp = categoryController.getCategoriesOf("Q5852");

        // Verify the response
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(List.of(coveringData, furnishingData), resp.getBody());

        verify(wikidataService, times(3)).getWikidataEntityFromId(anyString());
    }
}