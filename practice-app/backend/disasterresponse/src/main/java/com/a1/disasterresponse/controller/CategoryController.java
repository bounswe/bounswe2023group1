package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.EntityData;
import com.a1.disasterresponse.repository.EntityRepository;
import com.a1.disasterresponse.service.WikidataService;
import kotlin.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

    private final WikidataService wikidataService;
    private final EntityRepository relationRepository;

    public CategoryController(WikidataService wikidataService, EntityRepository relationRepository) {
        this.wikidataService = wikidataService;
        this.relationRepository = relationRepository;
    }

    @GetMapping("/category/findEntity")
    public ResponseEntity<EntityData.EntityRecord> findEntity(@RequestParam String query) {
        try {
            String id = wikidataService.searchForWikidataEntity(query);
            EntityData data = wikidataService.getWikidataEntityFromId(id);
            return new ResponseEntity<>(data.toRecord(), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/getCategoriesOf")
    public ResponseEntity<List<EntityData.EntityRecord>> getCategoriesOf(@RequestParam String id) {
        try {
            EntityData entityData = wikidataService.getWikidataEntityFromId(id);
            List<EntityData.EntityRecord> categories = new ArrayList<>();
            for (String category : entityData.categories()) {
                categories.add(wikidataService.getWikidataEntityFromId(category).toRecord());
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/category/saveEntity")
    public ResponseEntity<Void> saveEntity(@RequestParam String id) {
        try {
            EntityData entityData = wikidataService.getWikidataEntityFromId(id);
            relationRepository.save(entityData.toRecord());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/getEntities")
    public ResponseEntity<List<EntityData.EntityRecord>> getSavedEntities() {
        return new ResponseEntity<>(relationRepository.findAll(), HttpStatus.OK);
    }
}
