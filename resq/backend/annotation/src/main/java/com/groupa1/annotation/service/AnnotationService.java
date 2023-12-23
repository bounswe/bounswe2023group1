package com.groupa1.annotation.service;

import com.groupa1.annotation.entity.Annotation;
import com.groupa1.annotation.repository.AnnotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnotationService {

    @Autowired
    private AnnotationRepository annotationRepository;

    public List<Annotation> getAllAnnotations() {
        return annotationRepository.findAll();
    }

    public String createAnnotation(String id, String value) {
        Annotation annotation = new Annotation();
        annotation.setId(id);
        annotation.setValue(value);
        annotationRepository.save(annotation);
        return "Annotation created";
    }

    public String updateAnnotation(String id, String value) {
        Annotation annotation = annotationRepository.findById(id).orElse(null);
        if (annotation == null) {
            return "Annotation not found";
        }
        annotation.setValue(value);
        annotationRepository.save(annotation);
        return "Annotation updated";
    }

    public String deleteAnnotation(String id) {
        Annotation annotation = annotationRepository.findById(id).orElse(null);
        if (annotation == null) {
            return "Annotation not found";
        }
        annotationRepository.delete(annotation);
        return "Annotation deleted";
    }

    public Annotation getAnnotation(String id) {
        return annotationRepository.findById(id).orElse(null);
    }
}
