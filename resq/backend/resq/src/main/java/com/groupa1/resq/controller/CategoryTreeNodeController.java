package com.groupa1.resq.controller;

import com.groupa1.resq.entity.CategoryTreeNode;
import com.groupa1.resq.service.CategoryTreeNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/categorytreenode")
@Slf4j
public class CategoryTreeNodeController {

    @Autowired
    private CategoryTreeNodeService categoryTreeNodeService;

    @GetMapping("/getMainCategories")
    public ResponseEntity<Set<CategoryTreeNode>> getMainCategories() {
        log.info("Getting main categories in tree");
        return categoryTreeNodeService.getMainCategories();
    }

    @GetMapping("/getSubCategoryByName")
    public ResponseEntity<Set<CategoryTreeNode>> getSubCategoryByName(@RequestParam String name) {
        log.info("Getting subcategories of " + name);
        return categoryTreeNodeService.getSubCategoryByName(name);
    }

    @GetMapping("/getCategoryTree")
    public ResponseEntity<String> getCategoryTree() {
        log.info("Getting all category tree");
        return categoryTreeNodeService.getCategoryTree();
    }
}
