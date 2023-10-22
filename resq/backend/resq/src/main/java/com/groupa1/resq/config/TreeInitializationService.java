package com.groupa1.resq.config;

import com.groupa1.resq.entity.CategoryTreeNode;
import com.groupa1.resq.repository.CategoryTreeNodeRepository;
import com.groupa1.resq.service.CategoryTreeNodeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreeInitializationService {

    @Autowired
    private CategoryTreeNodeService categoryTreeNodeService;

    @Transactional
    public void initializeTree() {
        if (categoryTreeNodeService.isTreeEmpty()) {
            CategoryTreeNode root = new CategoryTreeNode();
            root.setData("Category");
            categoryTreeNodeService.save(root);

            CategoryTreeNode child1 = new CategoryTreeNode();
            child1.setData("Human Resource");
            child1.setParent(root);
            categoryTreeNodeService.save(child1);

            CategoryTreeNode child2 = new CategoryTreeNode();
            child2.setData("Clothing");
            child2.setParent(root);
            categoryTreeNodeService.save(child2);

            CategoryTreeNode child3 = new CategoryTreeNode();
            child3.setData("Shelter");
            child3.setParent(root);
            categoryTreeNodeService.save(child3);

            CategoryTreeNode child4 = new CategoryTreeNode();
            child4.setData("Tools and Equipment");
            child4.setParent(root);
            categoryTreeNodeService.save(child4);

            CategoryTreeNode child5 = new CategoryTreeNode();
            child5.setData("Food and Water");
            child5.setParent(root);
            categoryTreeNodeService.save(child5);

            CategoryTreeNode child6 = new CategoryTreeNode();
            child6.setData("Medical Supplies");
            child6.setParent(root);
            categoryTreeNodeService.save(child6);

            CategoryTreeNode child7 = new CategoryTreeNode();
            child7.setData("Transportation");
            child7.setParent(root);
            categoryTreeNodeService.save(child7);
        }
    }
}
