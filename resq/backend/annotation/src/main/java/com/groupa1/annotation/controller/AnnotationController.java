package com.groupa1.annotation.controller;

import com.groupa1.annotation.entity.Annotation;
import com.groupa1.annotation.service.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annotations")
public class AnnotationController {

    @Autowired
    private AnnotationService annotationService;

    @GetMapping("/")
    public List<Annotation> getAllAnnotations() {
        return annotationService.getAllAnnotations();
    }

    @GetMapping("/{id}")
    public Annotation getAnnotation(@PathVariable String id) {
        return annotationService.getAnnotation(id);
    }

    @PostMapping("/{id}")
    public String createAnnotation(@PathVariable String id, @RequestBody String value) {
        return annotationService.createAnnotation(id, value);
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
