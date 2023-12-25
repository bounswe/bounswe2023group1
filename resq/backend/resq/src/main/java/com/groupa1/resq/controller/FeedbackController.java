package com.groupa1.resq.controller;

import com.groupa1.resq.request.CreateFeedbackRequest;
import com.groupa1.resq.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/giveFeedback")
    public ResponseEntity<Object> giveFeedback(CreateFeedbackRequest feedback) {
        return feedbackService.giveFeedback(feedback);
    }


    @PreAuthorize("hasRole('RESPONDER')")
    @PostMapping("/deleteFeedback")
    public ResponseEntity<String> deleteFeedback(@RequestParam Long feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }

}
