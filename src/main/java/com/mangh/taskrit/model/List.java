package com.mangh.taskrit.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"tasks"})
public class List {

    @Id
    @GeneratedValue
    private UUID listId;

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
