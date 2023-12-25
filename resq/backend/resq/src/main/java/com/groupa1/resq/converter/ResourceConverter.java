package com.groupa1.resq.converter;

import com.groupa1.resq.dto.FileDto;
import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.entity.Resource;
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
        if(resource.getStatus() != null){
            resourceDto.setStatus(resource.getStatus());
        }

        if (resource.getSize() != null)
            resourceDto.setSize(resource.getSize());
        if (resource.getFile() != null){
            FileDto fileDto = new FileDto();
            fileDto.setFileName(resource.getFile().getFileName());
            fileDto.setFileUrl(resource.getFile().getFileUrl());
            fileDto.setFileType(resource.getFile().getFileType());
            resourceDto.setFile(fileDto);
        }
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
        if (resourceDto.getSize() != null)
            resource.setSize(resourceDto.getSize());
        return resource;
    }
}
