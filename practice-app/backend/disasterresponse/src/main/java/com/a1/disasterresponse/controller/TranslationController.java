package com.a1.disasterresponse.controller;

import com.a1.disasterresponse.service.TranslationService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TranslationController {

    private final TranslationService translationService;

    public TranslationController(TranslationService _translationService) {
        this.translationService = _translationService;
    }

    @GetMapping("/translation")
	public ResponseEntity<String> getTranslation(@RequestParam String sourceText, @RequestParam String destLang) {
		return new ResponseEntity<String>(translationService.translate(sourceText, destLang) ,HttpStatus.OK);
	}

    
}
