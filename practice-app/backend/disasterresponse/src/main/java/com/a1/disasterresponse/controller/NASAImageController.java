package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.NASAImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nasa")
public class NASAImageController {

    @Autowired
    private NASAImageService nasaImageService;

    @GetMapping("/search/{text}")
    public String searchImages(@PathVariable String text) {
        return nasaImageService.searchImages(text);
    }
}

