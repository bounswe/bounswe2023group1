package com.groupa1.resq.converter;

import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.entity.enums.ESize;
import com.groupa1.resq.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ResourceConverter {

    @Autowired
    private UserRepository userRepository;

    public ResourceDto convertToDto(Resource resource){
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(resource.getId());
        resourceDto.setSenderId(resource.getSender().getId());
        if (resource.getReceiver() != null){
            resourceDto.setReceiverId(resource.getReceiver().getId());
        }
        resourceDto.setCategoryTreeId(resource.getCategoryTreeId());
        resourceDto.setGender(resource.getGender());
        resourceDto.setQuantity(resource.getQuantity());
        resourceDto.setLatitude(resource.getLatitude());
        resourceDto.setLongitude(resource.getLongitude());
        resourceDto.setCreatedDate(resource.getCreatedAt());
        resourceDto.setSize(resource.getSize().toString());
        return resourceDto;

    }

    public Resource convertToEntity(ResourceDto resourceDto){
        Resource resource = new Resource();
        resource.setId(resourceDto.getId());
        Long senderId = resourceDto.getSenderId();
        Long receiverId = resourceDto.getReceiverId();
        if (senderId != null){
            resource.setSender(userRepository.findById(senderId).orElse(null));
        }
        if (receiverId != null){
            resource.setReceiver(userRepository.findById(receiverId).orElse(null));
        }
        resource.setCategoryTreeId(resourceDto.getCategoryTreeId());
        resource.setGender(resourceDto.getGender());
        resource.setQuantity(resourceDto.getQuantity());
        resource.setLatitude(resourceDto.getLatitude());
        resource.setLongitude(resourceDto.getLongitude());
        resource.setCreatedAt(resourceDto.getCreatedDate());
        resource.setSize(ESize.valueOf(resourceDto.getSize()));
        return resource;
    }
}
