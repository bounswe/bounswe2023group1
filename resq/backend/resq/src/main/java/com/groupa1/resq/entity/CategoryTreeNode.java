package com.groupa1.resq.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "CATEGORY_TREE")
@Data
@TableGenerator(name = "categoryTreeGen", initialValue = 0, allocationSize = 1)
@EqualsAndHashCode(exclude = {"parent", "children"})
@ToString(exclude = {"parent", "children"})
public class CategoryTreeNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private CategoryTreeNode parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<CategoryTreeNode> children;
}
