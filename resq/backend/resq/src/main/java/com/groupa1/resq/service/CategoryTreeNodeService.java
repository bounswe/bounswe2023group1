package com.groupa1.resq.service;

import com.groupa1.resq.entity.CategoryTreeNode;
import com.groupa1.resq.repository.CategoryTreeNodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

@Service
@Slf4j
public class CategoryTreeNodeService {
    @Autowired
    private CategoryTreeNodeRepository categoryTreeNodeRepository;

    public boolean isTreeEmpty() {
        return categoryTreeNodeRepository.count() == 0;
    }

    public void save(CategoryTreeNode root) {
        categoryTreeNodeRepository.save(root);
    }

    public CategoryTreeNode findNodeByData(String searchData) {
        CategoryTreeNode root = categoryTreeNodeRepository.findRoot();
        Queue<CategoryTreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            CategoryTreeNode currentNode = queue.poll();
            if (currentNode.getData().equals(searchData)) {
                return currentNode; // Node found
            }

            if(currentNode.getChildren() == null) {
                continue;
            }
            for (CategoryTreeNode child : currentNode.getChildren()) {
                queue.add(child);
            }
        }

        return null; // Node not found
    }

    public String findNodePathToRoot(String searchData) {
        CategoryTreeNode root = findNodeByData(searchData);
        if (root == null) {
            return null;
        }
        StringBuilder path = new StringBuilder();
        path.insert(0, root.getData());
        while (root.getParent() != null) {
            root = root.getParent();
            path.insert(0, root.getData() + "_");
        }
        return path.toString();
    }

    public CategoryTreeNode findNodeByPath(String path) {
        if(path == null || path.equals("")) return null;
        String[] pathArray = path.split("_");
        CategoryTreeNode currentNode = categoryTreeNodeRepository.findRoot();
        if(pathArray.length == 1) {
            return currentNode;
        }
        for (int i = 1; i<pathArray.length; i++) {
            String nodeData = pathArray[i];
            boolean found = false;
            for (CategoryTreeNode child : currentNode.getChildren()) {
                if (child.getData().equals(nodeData)) {
                    currentNode = child;
                    found = true;
                    break;
                }
            }
            if (!found) {
                return null;
            }
        }
        return currentNode;
    }

    public ResponseEntity<Set<CategoryTreeNode>> getMainCategories() {
        CategoryTreeNode categoryTreeNode = categoryTreeNodeRepository.findRoot();
        if(categoryTreeNode == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryTreeNode.getChildren());
    }

    public ResponseEntity<Set<CategoryTreeNode>> getSubCategoryByName(String name) {
        CategoryTreeNode categoryTreeNode = findNodeByData(name);
        if(categoryTreeNode == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryTreeNode.getChildren());
    }
}
