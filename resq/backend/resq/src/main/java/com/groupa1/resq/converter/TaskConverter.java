package com.groupa1.resq.converter;

import com.groupa1.resq.dto.ActionDto;
import com.groupa1.resq.dto.FeedbackDto;
import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.dto.TaskDto;
import com.groupa1.resq.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class TaskConverter {

    @Autowired
    private ActionConverter actionConverter;

    @Autowired
    private ResourceConverter resourceConverter;

    @Autowired
    private FeedbackConverter feedbackConverter;


    public TaskDto convertToDto(Task task){
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setAssignee(task.getAssignee().getId());
        taskDto.setAssigner(task.getAssigner().getId());
        taskDto.setStatus(task.getStatus());
        Set<ActionDto> taskActions = new HashSet<>(task.getActions().stream()
                .map(action -> actionConverter.convertToDto(action)).toList());
        taskDto.setActions(taskActions);
        taskDto.setDescription(task.getDescription());
        Set<ResourceDto> taskResources = new HashSet<>(task.getResources().stream()
                .map(resource ->resourceConverter.convertToDto(resource)).toList());
        taskDto.setResources(taskResources);
        Set<FeedbackDto> taskFeedbacks = new HashSet<>(task.getFeedbacks().stream()
                .map(feedback ->feedbackConverter.convertToDto(feedback)).toList());
        taskDto.setFeedbacks(taskFeedbacks);
        taskDto.setUrgency(task.getUrgency());
        taskDto.setCreatedDate(task.getCreatedAt());
        return taskDto;

    }
}
