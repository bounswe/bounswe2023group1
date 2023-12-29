package com.groupa1.resq.service;

import com.groupa1.resq.entity.CategoryTreeNode;
import com.groupa1.resq.repository.CategoryTreeNodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryTreeNodeServiceTest {

    @InjectMocks
    private CategoryTreeNodeService categoryTreeNodeService;

    @Mock
    private CategoryTreeNodeRepository categoryTreeNodeRepository;

    @Test
    void test_isTreeEmpty_shouldReturnTrueIfEmpty() {
        // given

        // when
        when(categoryTreeNodeRepository.count()).thenReturn(0L);
        // then
        Boolean result = categoryTreeNodeService.isTreeEmpty();
        assertEquals(true, result);
    }

    @Test
    void test_isTreeEmpty_shouldReturnFalseIfNotEmpty() {
        // given

        // when
        when(categoryTreeNodeRepository.count()).thenReturn(1L);
        // then
        Boolean result = categoryTreeNodeService.isTreeEmpty();
        assertEquals(false, result);
    }

    @Test
    void test_findNodeByData_ifExists_shouldReturnNode() {
        //given
        String mockSearchData = "test-data";
        CategoryTreeNode mockRoot = new CategoryTreeNode();
        mockRoot.setData("test-data");

        //when
        when(categoryTreeNodeRepository.findRoot()).thenReturn(mockRoot);

        //then
        CategoryTreeNode result = categoryTreeNodeService.findNodeByData(mockSearchData);
        assertEquals(mockRoot, result);
    }

    @Test
    void test_findNodeByData_ifNotExists_shouldReturnNull() {
        //given
        String mockSearchData = "test-data";
        CategoryTreeNode mockRoot = new CategoryTreeNode();
        mockRoot.setData("test-data-parent");
        mockRoot.setParent(null);
        mockRoot.setChildren(Set.of());

        //when
        when(categoryTreeNodeRepository.findRoot()).thenReturn(mockRoot);

        //then
        CategoryTreeNode result = categoryTreeNodeService.findNodeByData(mockSearchData);
        assertEquals(null, result);
    }

    @Test
    void test_findNodePathToRoot() {
        //given
        String mockSearchData = "test-data";
        CategoryTreeNode mockRoot = new CategoryTreeNode();
        mockRoot.setData("test-data-parent");
        mockRoot.setParent(null);

        CategoryTreeNode childRoot = new CategoryTreeNode();
        childRoot.setData("test-data");
        childRoot.setParent(mockRoot);

        //when
        when(categoryTreeNodeRepository.findRoot()).thenReturn(childRoot);

        //then
        String result = categoryTreeNodeService.findNodePathToRoot(mockSearchData);
        assertEquals("test-data-parent_test-data", result);
    }

    @Test
    void test_findNodeByPath() {
        //given
        String mockPath = "test-data-parent_test-data";
        CategoryTreeNode mockRoot = new CategoryTreeNode();
        mockRoot.setData("test-data-parent");
        mockRoot.setParent(null);

        CategoryTreeNode childRoot = new CategoryTreeNode();
        childRoot.setData("test-data");
        childRoot.setParent(mockRoot);

        mockRoot.setChildren(Set.of(childRoot));


        //when
        when(categoryTreeNodeRepository.findRoot()).thenReturn(mockRoot);

        //then
        CategoryTreeNode result = categoryTreeNodeService.findNodeByPath(mockPath);
        assertEquals(childRoot, result);
    }

    @Test
    void test_getMainCategories() {
        //given
        CategoryTreeNode mockRoot = new CategoryTreeNode();
        mockRoot.setData("test-data-parent");
        mockRoot.setParent(null);

        CategoryTreeNode mainCategory1 = new CategoryTreeNode();
        mainCategory1.setData("main-category-1");
        mainCategory1.setParent(mockRoot);

        CategoryTreeNode mainCategory2 = new CategoryTreeNode();
        mainCategory2.setData("main-category-2");
        mainCategory2.setParent(mockRoot);

        mockRoot.setChildren(Set.of(mainCategory1, mainCategory2));

        //when
        when(categoryTreeNodeRepository.findRoot()).thenReturn(mockRoot);

        //then
        ResponseEntity<Set<CategoryTreeNode>> result = categoryTreeNodeService.getMainCategories();
        assertEquals(Set.of(mainCategory1, mainCategory2), result.getBody());
    }

    @Test
    void test_getSubCategoryByName() {
        //given
        String mockSearchData = "test-data-parent";
        CategoryTreeNode mockRoot = new CategoryTreeNode();
        mockRoot.setData("test-data-parent");
        mockRoot.setParent(null);

        CategoryTreeNode childRoot = new CategoryTreeNode();
        childRoot.setData("test-data");
        childRoot.setParent(mockRoot);

        mockRoot.setChildren(Set.of(childRoot));

        //when
        when(categoryTreeNodeRepository.findRoot()).thenReturn(mockRoot);

        //then
        ResponseEntity<Set<CategoryTreeNode>> result = categoryTreeNodeService.getSubCategoryByName(mockSearchData);
        assertEquals(Set.of(childRoot), result.getBody());
    }
}