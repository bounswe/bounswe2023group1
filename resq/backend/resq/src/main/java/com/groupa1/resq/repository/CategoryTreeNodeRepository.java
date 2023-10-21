package com.groupa1.resq.repository;

import com.groupa1.resq.entity.CategoryTreeNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryTreeNodeRepository extends JpaRepository<CategoryTreeNode, Long> {

    @Query("SELECT c FROM CategoryTreeNode c WHERE c.parent IS NULL")
    CategoryTreeNode findRoot();
}
