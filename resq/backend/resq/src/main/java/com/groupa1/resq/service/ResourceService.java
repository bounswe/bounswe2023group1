package com.groupa1.resq.service;

import com.groupa1.resq.converter.ResourceConverter;
import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.entity.Resource;
import com.groupa1.resq.entity.User;
import com.groupa1.resq.entity.enums.EResourceStatus;
import com.groupa1.resq.entity.enums.ESize;
import com.groupa1.resq.exception.EntityNotFoundException;
import com.groupa1.resq.repository.ResourceRepository;
import com.groupa1.resq.request.CreateResourceRequest;
import com.groupa1.resq.specification.ResourceSpecifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryTreeNodeService categoryTreeNodeService;

    @Autowired
    private ResourceConverter resourceConverter;

    public ResponseEntity<Object> createResource(CreateResourceRequest createResourceRequest) {
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
        resource.setSize(ESize.valueOf(createResourceRequest.getSize()));
        resource.setStatus(EResourceStatus.AVAILABLE); // default
        resource.setGender(createResourceRequest.getGender());
        Long resourceId = resourceRepository.save(resource).getId();
        return ResponseEntity.ok(resourceId);
    }
    public ResponseEntity<String> updateResource(CreateResourceRequest createResourceRequest, Long resourceId){
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + resourceId));
        resource.setGender(createResourceRequest.getGender());
        resource.setQuantity(createResourceRequest.getQuantity());
        resource.setLatitude(createResourceRequest.getLatitude());
        resource.setLongitude(createResourceRequest.getLongitude());
        resource.setCategoryTreeId(createResourceRequest.getCategoryTreeId());
        resource.setSize(ESize.valueOf(createResourceRequest.getSize()));
        resource.setStatus(createResourceRequest.getStatus());
        resourceRepository.save(resource);
        return ResponseEntity.ok("Resource updated successfully");
    }
    public ResponseEntity<ResourceDto> viewResource(Long resourceId){
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + resourceId));
        return ResponseEntity.ok(resourceConverter.convertToDto(resource));
    }
    public ResponseEntity<String> deleteResource(Long resourceId){
        Resource resource = resourceRepository.findById(resourceId).orElseThrow(() -> new EntityNotFoundException("Resource not found with id: " + resourceId));
        resourceRepository.delete(resource);
        return ResponseEntity.ok("Resource deleted successfully");
    }

    public ResponseEntity<List<ResourceDto>> filterResource(BigDecimal latitude, BigDecimal longitude, String categoryTreeId, Long userId, EResourceStatus status){
        Specification<Resource> spec = Specification.where(null);

        if (longitude != null && latitude != null) {

            spec = spec.and(ResourceSpecifications.hasLongitude(longitude));
            spec = spec.and(ResourceSpecifications.hasLatitude(latitude));

        }
        if (categoryTreeId != null) {
            spec = spec.and(ResourceSpecifications.hasCategoryTreeId(categoryTreeId));
        }
        if (userId != null) {
            spec = spec.and(ResourceSpecifications.hasOwnerId(userId));
        }
        if (status != null){
            spec = spec.and(ResourceSpecifications.hasStatus(status));
        }
        return ResponseEntity.ok(resourceRepository.findAll(spec).stream().map(resource -> resourceConverter.convertToDto(resource)).toList());

    }
    public ResponseEntity<List<ResourceDto>> filterByDistance(BigDecimal latitude, BigDecimal longitude, BigDecimal distance){
        return ResponseEntity.ok(resourceRepository.filterByDistance(latitude, longitude, distance).stream().map(resource -> resourceConverter.convertToDto(resource)).toList());
    }

    public ResponseEntity<List<ResourceDto>> filterResourceRectangularScope(BigDecimal latitude1, BigDecimal longitude1, BigDecimal latitude2, BigDecimal longitude2, String categoryTreeId, Long userId){
        Specification<Resource> spec = Specification.where(null);

        if (longitude1 != null && latitude1 != null && longitude2 != null && latitude2 != null) {

            spec = spec.and(ResourceSpecifications.isWithinRectangleScope(longitude1, longitude2, latitude1, latitude2));

        }
        if (categoryTreeId != null) {
            spec = spec.and(ResourceSpecifications.hasCategoryTreeId(categoryTreeId));
        }
        if (userId != null) {
            spec = spec.and(ResourceSpecifications.hasOwnerId(userId));
        }
        return ResponseEntity.ok(resourceRepository.findAll(spec).stream().map(resource -> resourceConverter.convertToDto(resource)).toList());

    }
}
