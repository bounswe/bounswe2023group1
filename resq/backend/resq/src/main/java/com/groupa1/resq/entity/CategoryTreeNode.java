package com.groupa1.resq.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table( name = "CATEGORY_TREE")
@Data
public class CategoryTreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryTreeNode parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CategoryTreeNode> children;
}
