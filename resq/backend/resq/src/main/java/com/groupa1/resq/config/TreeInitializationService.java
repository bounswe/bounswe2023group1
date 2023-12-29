package com.groupa1.resq.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupa1.resq.entity.CategoryTreeNode;
import com.groupa1.resq.repository.CategoryTreeNodeRepository;
import com.groupa1.resq.service.CategoryTreeNodeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

@Service
public class TreeInitializationService {

    @Autowired
    private CategoryTreeNodeService categoryTreeNodeService;

    private final String filePath = "category-tree.json";

    @Value("${category-tree.reset}")
    private boolean categoryTreeReset;

    @Transactional
    public void initializeTree() {
        if(categoryTreeReset) {
            categoryTreeNodeService.deleteAll();
        }
        if (categoryTreeNodeService.isTreeEmpty() || categoryTreeReset) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath)){
                if (inputStream != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(inputStream);
                    buildCategoryTree(rootNode, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exception
            }
        }
    }

    private CategoryTreeNode buildCategoryTree(JsonNode node, CategoryTreeNode parent) {
        CategoryTreeNode categoryTreeNode = new CategoryTreeNode();
        categoryTreeNode.setData(node.get("data").asText());
        categoryTreeNode.setParent(parent);

        Set<CategoryTreeNode> children = new HashSet<>();
        if (node.has("children")) {
            for (JsonNode childNode : node.get("children")) {
                CategoryTreeNode child = buildCategoryTree(childNode, categoryTreeNode);
                children.add(child);
            }
        }

        categoryTreeNode.setChildren(children);
        categoryTreeNodeService.save(categoryTreeNode);
        return categoryTreeNode;
    }
}
