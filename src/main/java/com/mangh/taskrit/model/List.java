package com.mangh.taskrit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;



@Entity
@Data
@NoArgsConstructor
public class List {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(length = 6)
    private String colour;

    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board board;

    @Column
    @OneToMany(mappedBy = "list", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private java.util.List<Task> tasks = new ArrayList<>();
}
