package com.groupa1.resq.converter;

import com.groupa1.resq.dto.FeedbackDto;
import com.groupa1.resq.entity.Feedback;
import org.springframework.stereotype.Service;

@Service
public class FeedbackConverter {

    public FeedbackDto convertToDto(Feedback feedback){
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(feedback.getId());
        feedbackDto.setTaskId(feedback.getTask().getId());
        feedbackDto.setUserId(feedback.getCreator().getId());
        feedbackDto.setMessage(feedback.getMessage());
        feedbackDto.setCreatedDate(feedback.getCreatedAt());
        return feedbackDto;
    }
}
