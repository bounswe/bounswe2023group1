package com.groupa1.resq.service;

import com.groupa1.resq.dto.FeedbackDto;
import com.groupa1.resq.entity.Feedback;
import com.groupa1.resq.entity.Task;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.FeedbackRepository;
import com.groupa1.resq.repository.TaskRepository;
import com.groupa1.resq.repository.UserRepository;
import com.groupa1.resq.request.CreateFeedbackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private TaskRepository taskRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;



    public ResponseEntity<Object> giveFeedback(
            CreateFeedbackRequest feedbackRequest){
        Task task = taskRepository.findById(feedbackRequest.getTaskId()).orElseThrow(()-> new EntityNotFoundException("No task found"));
        User user = userRepository.findById(feedbackRequest.getUserId()).orElseThrow(()-> new EntityNotFoundException("No user found"));
        Feedback feedback = new Feedback();
        feedback.setCreator(user);
        feedback.setMessage(feedbackRequest.getMessage());
        feedback.setTask(task);
        task.getFeedbacks().add(feedback);
        // TODO: send notification to coordinator
        taskRepository.save(task);
        return ResponseEntity.ok("Feedback saved successfully");
    }
    public ResponseEntity<String> deleteFeedback(Long feedbackId){
        Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
        if(feedback == null){
            return ResponseEntity.badRequest().body("No feedback found");
        }
        feedbackRepository.delete(feedback);
        return ResponseEntity.ok("Feedback deleted successfully");

    }



}
