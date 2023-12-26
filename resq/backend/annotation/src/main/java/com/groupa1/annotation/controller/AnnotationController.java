package com.groupa1.annotation.controller;

import com.groupa1.annotation.entity.Annotation;
import com.groupa1.annotation.service.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/annotations")
public class AnnotationController {

    @Autowired
    private AnnotationService annotationService;

    @GetMapping("/")
    public String getAllAnnotations() {
        return annotationService.getAllAnnotations();
    }

    @GetMapping("/{id}")
    public Annotation getAnnotation(@PathVariable String id) {
        return annotationService.getAnnotation(id);
    }

    @PostMapping("/")
    public String createAnnotation(@RequestBody String value) {
        return annotationService.createAnnotation(value);
    }

    @PutMapping("/{id}")
    public String updateAnnotation(@PathVariable String id, @RequestBody String value) {
        return annotationService.updateAnnotation(id, value);
    }

    @DeleteMapping("/{id}")
    public String deleteAnnotation(@PathVariable String id) {
        return annotationService.deleteAnnotation(id);
    }

}
