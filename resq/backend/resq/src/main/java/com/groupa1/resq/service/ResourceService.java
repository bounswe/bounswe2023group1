package com.groupa1.resq.service;

import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.repository.ResourceRepository;
import com.groupa1.resq.request.CreateResourceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryTreeNodeService categoryTreeNodeService;

    public ResponseEntity<String> createResource(CreateResourceRequest createResourceRequest) {
        if(createResourceRequest.getSenderId() == null) {
            log.error("Sender id is null");
            return ResponseEntity.badRequest().body("Sender id is null");
        }
        // Request's categoryTreeId will be something like: 1_3_6_11 and so on. These are node_ids kept in database.
        // We will provide the categories one by one to the frontend when users are creating a resource.
        // Then, frontend will send the request with the categoryTreeId as the example above. Then, we store it in db after checking
        // that path exists.
        if(categoryTreeNodeService.findNodeByPath(createResourceRequest.getCategoryTreeId()) == null) {
            log.error("Category tree id is invalid");
            return ResponseEntity.badRequest().body("Category tree id is invalid");

        }
        User user = userService.findById(createResourceRequest.getSenderId());
        Resource resource = new Resource();
        resource.setSender(user);
        resource.setLongitude(createResourceRequest.getLongitude());
        resource.setLatitude(createResourceRequest.getLatitude());
        resource.setQuantity(createResourceRequest.getQuantity());
        resource.setCategoryTreeId(createResourceRequest.getCategoryTreeId());
        resourceRepository.save(resource);
        return ResponseEntity.ok("Resource created successfully");
    }
}
