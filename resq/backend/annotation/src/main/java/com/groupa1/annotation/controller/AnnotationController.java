package com.groupa1.annotation.controller;

import com.groupa1.annotation.entity.Annotation;
import com.groupa1.annotation.service.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annotation")
public class AnnotationController {

    @Autowired
    private AnnotationService annotationService;

    @GetMapping("/getAllAnnotations")
    public List<Annotation> getAllAnnotations() {
        return annotationService.getAllAnnotations();
    }

    @GetMapping("/{id}")
    public Annotation getAnnotation(@PathVariable String id) {
        return annotationService.getAnnotation(id);
    }

    @PostMapping("/createAnnotation")
    public String createAnnotation(@RequestParam String id, @RequestParam String value) {
        return annotationService.createAnnotation(id, value);
    }

    @PutMapping("/updateAnnotation")
    public String updateAnnotation(@RequestParam String id, @RequestParam String value) {
        return annotationService.updateAnnotation(id, value);
    }

    @DeleteMapping("/deleteAnnotation")
    public String deleteAnnotation(@RequestParam String id) {
        return annotationService.deleteAnnotation(id);
    }

}
