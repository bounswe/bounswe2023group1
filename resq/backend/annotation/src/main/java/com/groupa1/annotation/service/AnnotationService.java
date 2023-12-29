package com.groupa1.annotation.service;

import com.groupa1.annotation.entity.Annotation;
import com.groupa1.annotation.repository.AnnotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnotationService {

    @Autowired
    private AnnotationRepository annotationRepository;

    public String getAllAnnotations() {
        return "[" + annotationRepository
                .findAll()
                .stream()
                .map(Annotation::getValue)
                .collect(Collectors.joining(", ")) + "]";
    }

    public String createAnnotation(String value) {
        String id = UUID.randomUUID().toString();
        String toAdd = "\\\"id\\\":\\\"https://annotation.resq.org.tr/annotations/" + id + "\\\",";
        String v2 = "\"{\\" + toAdd + value.substring(3);
        return createAnnotation(id, v2);
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
            return createAnnotation(id, value);
        } else {
            annotation.setValue(value);
            annotationRepository.save(annotation);
            return "Annotation updated";
        }
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
