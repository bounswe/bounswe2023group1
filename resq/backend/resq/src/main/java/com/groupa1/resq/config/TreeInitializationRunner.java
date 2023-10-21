package com.groupa1.resq.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class TreeInitializationRunner implements ApplicationRunner {
    private final TreeInitializationService treeInitializationService;

    public TreeInitializationRunner(TreeInitializationService treeInitializationService) {
        this.treeInitializationService = treeInitializationService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        treeInitializationService.initializeTree();
    }
}
