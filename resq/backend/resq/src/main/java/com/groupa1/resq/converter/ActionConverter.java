package com.groupa1.resq.converter;

import com.groupa1.resq.dto.ActionDto;
import com.groupa1.resq.entity.Action;
import org.springframework.stereotype.Service;

@Service
public class ActionConverter {

    public ActionDto convertToDto(Action action){
        ActionDto actionDto = new ActionDto();
        actionDto.setId(action.getId());
        actionDto.setTaskId(action.getTask().getId());
        if (action.getVerifier() != null) {
            actionDto.setVerifierId(action.getVerifier().getId());
        }
        actionDto.setDescription(action.getDescription());
        actionDto.setCompleted(action.isCompleted());
        actionDto.setStartLatitude(action.getStartLatitude());
        actionDto.setStartLongitude(action.getStartLongitude());
        actionDto.setEndLatitude(action.getEndLatitude());
        actionDto.setEndLongitude(action.getEndLongitude());
        actionDto.setDueDate(action.getDueDate());
        actionDto.setCreatedDate(action.getCreatedAt());
        return actionDto;

    }
}
