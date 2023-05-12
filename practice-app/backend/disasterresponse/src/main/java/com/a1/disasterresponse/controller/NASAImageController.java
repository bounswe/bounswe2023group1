package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.NASAImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nasa")
public class NASAImageController {

    @Autowired
    private NASAImageService nasaImageService;

    @GetMapping("/search")
    public String searchImages(@RequestParam String text) {
        return nasaImageService.searchImages(text);
    }
}

