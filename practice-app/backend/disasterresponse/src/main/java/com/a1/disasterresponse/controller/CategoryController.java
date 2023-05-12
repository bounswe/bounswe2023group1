package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.model.EntityData;
import com.a1.disasterresponse.service.WikidataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryController {

    WikidataService wikidataService;

    @GetMapping("/getEntityFor")
    public ResponseEntity<EntityData> categoriesOf(@RequestParam String query) {
        try {
            String id = wikidataService.searchForWikidataEntity(query);
            EntityData entityData = wikidataService.getWikidataEntityFromId(id);
            List<String> categories = new ArrayList<>();
            for (String category : entityData.categories()) {
                categories.add(wikidataService.getWikidataEntityFromId(category).labels().get("en"));
            }
            return new ResponseEntity<>(entityData, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
