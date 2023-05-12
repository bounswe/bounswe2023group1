package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.EntityData;
import com.a1.disasterresponse.service.WikidataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private final WikidataService wikidataService;

    public CategoryController(WikidataService wikidataService) {
        this.wikidataService = wikidataService;
    }

    @GetMapping("/getIdOf")
    public ResponseEntity<String> idOf(@RequestParam String query) {
        try {
            String id = wikidataService.searchForWikidataEntity(query);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCategoriesOf")
    public ResponseEntity<List<EntityData>> getCategoriesOf(@RequestParam String id) {
        try {
            EntityData entityData = wikidataService.getWikidataEntityFromId(id);
            List<EntityData> categories = new ArrayList<>();
            for (String category : entityData.categories()) {
                categories.add(wikidataService.getWikidataEntityFromId(category));
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
