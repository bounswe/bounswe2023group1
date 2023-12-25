package com.groupa1.resq.service;


import com.groupa1.resq.config.AmazonClient;
import com.groupa1.resq.converter.ResourceConverter;
import com.groupa1.resq.dto.ResourceDto;
import com.groupa1.resq.entity.*;
import com.groupa1.resq.entity.enums.EGender;
import com.groupa1.resq.entity.enums.EResourceStatus;
import com.groupa1.resq.entity.enums.ESize;
import com.groupa1.resq.repository.ResourceRepository;
import com.groupa1.resq.request.CreateResourceRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceServiceTest {

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private UserService userService;

    @Mock
    private CategoryTreeNodeService categoryTreeNodeService;

    @Mock
    private AmazonClient amazonClient;

    @Mock
    private ResourceConverter resourceConverter;


    @Test
    void test_createResource(){
        // Given
        CreateResourceRequest createResourceRequest = new CreateResourceRequest();
        createResourceRequest.setSenderId(1L);
        createResourceRequest.setLongitude(BigDecimal.valueOf(123.456));
        createResourceRequest.setLatitude(BigDecimal.valueOf(78.910));
        createResourceRequest.setQuantity(5);
        createResourceRequest.setCategoryTreeId("1_3_6_11");
        createResourceRequest.setSize(ESize.valueOf("L"));
        createResourceRequest.setGender(EGender.valueOf("MALE"));

        MultipartFile file = mock(MultipartFile.class);

        User user = new User();
        user.setId(1L);

        Resource resource = new Resource();
        resource.setId(1L);
        resource.setSender(user);
        resource.setLongitude(BigDecimal.valueOf(123.456));
        resource.setLatitude(BigDecimal.valueOf(78.910));
        resource.setQuantity(5);
        resource.setCategoryTreeId("1_3_6_11");
        resource.setSize(ESize.valueOf("L"));
        resource.setStatus(EResourceStatus.AVAILABLE);
        resource.setGender(EGender.valueOf("MALE"));

        File fileEntity = new File();
        fileEntity.setId(1L);

        when(amazonClient.uploadFile(file)).thenReturn(fileEntity);
        when(userService.findById(1L)).thenReturn(user);
        when(resourceRepository.save(any(Resource.class))).thenReturn(resource);
        when(categoryTreeNodeService.findNodeByPath("1_3_6_11")).thenReturn(new CategoryTreeNode());

        // When
        ResponseEntity<Object> responseEntity = resourceService.createResource(createResourceRequest, file);

        // Then
        verify(categoryTreeNodeService, times(1)).findNodeByPath("1_3_6_11");
        verify(userService, times(1)).findById(1L);
        verify(amazonClient, times(1)).uploadFile(file);
        verify(resourceRepository, times(1)).save(any(Resource.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1L, responseEntity.getBody());
    }

    @Test
    void test_updateResource() {
        // Given
        Long resourceId = 1L;

        CreateResourceRequest createResourceRequest = new CreateResourceRequest();
        createResourceRequest.setGender(EGender.valueOf("MALE"));
        createResourceRequest.setQuantity(5);
        createResourceRequest.setLatitude(BigDecimal.valueOf(123.456));
        createResourceRequest.setLongitude(BigDecimal.valueOf(78.910));
        createResourceRequest.setCategoryTreeId("1_3_6_11");
        createResourceRequest.setSize(ESize.valueOf("L"));
        createResourceRequest.setStatus(EResourceStatus.AVAILABLE);

        Resource resource = new Resource();
        resource.setId(resourceId);
        resource.setGender(EGender.valueOf("FEMALE"));
        resource.setQuantity(3);
        resource.setLatitude(BigDecimal.valueOf(12.345));
        resource.setLongitude(BigDecimal.valueOf(67.890));
        resource.setCategoryTreeId("2_4_7_12");
        resource.setSize(ESize.valueOf("XL"));
        resource.setStatus(EResourceStatus.IN_TASK);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // When
        ResponseEntity<String> responseEntity = resourceService.updateResource(createResourceRequest, resourceId);

        // Then
        verify(resourceRepository, times(1)).findById(resourceId);
        verify(resourceRepository, times(1)).save(any(Resource.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Resource updated successfully", responseEntity.getBody());
    }

    @Test
    void test_viewResource(){
        // Given
        Long resourceId = 1L;

        Resource resource = new Resource();
        resource.setId(resourceId);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // When
        ResponseEntity<ResourceDto> responseEntity = resourceService.viewResource(resourceId);

        // Then
        verify(resourceRepository, times(1)).findById(resourceId);
        verify(resourceConverter, times(1)).convertToDto(resource);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void test_deleteResource(){
        // Given
        Long resourceId = 1L;

        Resource resource = new Resource();
        resource.setId(resourceId);

        when(resourceRepository.findById(resourceId)).thenReturn(Optional.of(resource));

        // When
        ResponseEntity<String> responseEntity = resourceService.deleteResource(resourceId);

        // Then
        verify(resourceRepository, times(1)).findById(resourceId);
        verify(resourceRepository, times(1)).delete(resource);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Resource deleted successfully", responseEntity.getBody());
    }

    @Test
    void test_filterResource(){
        // Given
        BigDecimal latitude = BigDecimal.valueOf(123.456);
        BigDecimal longitude = BigDecimal.valueOf(78.910);
        String categoryTreeId = "1_3_6_11";
        Long userId = 1L;
        EResourceStatus status = EResourceStatus.AVAILABLE;
        Long receiverId = 2L;

        Resource resource = new Resource();
        resource.setId(1L);
        resource.setLatitude(latitude);
        resource.setLongitude(longitude);
        resource.setCategoryTreeId(categoryTreeId);
        resource.setSender(new User());
        resource.setStatus(status);
        resource.setReceiver(new User());

        when(resourceRepository.findAll(any(Specification.class))).thenReturn(List.of(resource));

        // When
        ResponseEntity<List<ResourceDto>> responseEntity = resourceService.filterResource(latitude, longitude, categoryTreeId, userId, status, receiverId);

        // Then
        verify(resourceRepository, times(1)).findAll(any(Specification.class));
        verify(resourceConverter, times(1)).convertToDto(resource);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    void test_filterByDistance(){
        // Given
        BigDecimal latitude = BigDecimal.valueOf(123.456);
        BigDecimal longitude = BigDecimal.valueOf(78.910);
        BigDecimal distance = BigDecimal.valueOf(100);

        Resource resource = new Resource();
        resource.setId(1L);
        resource.setLatitude(latitude);
        resource.setLongitude(longitude);
        resource.setCategoryTreeId("1_3_6_11");
        resource.setSender(new User());
        resource.setStatus(EResourceStatus.AVAILABLE);
        resource.setReceiver(new User());

        when(resourceRepository.filterByDistance(latitude, longitude, distance)).thenReturn(List.of(resource));

        // When
        ResponseEntity<List<ResourceDto>> responseEntity = resourceService.filterByDistance(latitude, longitude, distance);

        // Then
        verify(resourceRepository, times(1)).filterByDistance(latitude, longitude, distance);
        verify(resourceConverter, times(1)).convertToDto(resource);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
    }

    @Test
    void test_filterResourceRectangularScope(){
        // Given
        BigDecimal latitude1 = BigDecimal.valueOf(123.456);
        BigDecimal longitude1 = BigDecimal.valueOf(78.910);
        BigDecimal latitude2 = BigDecimal.valueOf(123.456);
        BigDecimal longitude2 = BigDecimal.valueOf(78.910);
        String categoryTreeId = "1_3_6_11";
        Long userId = 1L;
        Long receiverId = 2L;

        Resource resource = new Resource();
        resource.setId(1L);
        resource.setLatitude(latitude1);
        resource.setLongitude(longitude1);
        resource.setCategoryTreeId(categoryTreeId);
        resource.setSender(new User());
        resource.setReceiver(new User());

        when(resourceRepository.findAll(any(Specification.class))).thenReturn(List.of(resource));

        // When
        ResponseEntity<List<ResourceDto>> responseEntity = resourceService.filterResourceRectangularScope(latitude1, longitude1, latitude2, longitude2, categoryTreeId, userId, receiverId);

        // Then
        verify(resourceRepository, times(1)).findAll(any(Specification.class));
        verify(resourceConverter, times(1)).convertToDto(resource);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
    }
}