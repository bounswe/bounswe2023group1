package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.NASAImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import com.a1.disasterresponse.model.NasaData;

@RestController
@RequestMapping("/nasa")
public class NASAImageController {

    @Autowired
    private NASAImageService nasaImageService;

    @GetMapping("/search")
    public String searchImages(@RequestParam String text) {
        return nasaImageService.searchImages(text);
    }

    @PostMapping("/save")
    public void saveNasaData(@RequestBody NasaData nasadata) {
        nasaImageService.saveNasaData(nasadata);
    }
}
