package com.mangh.taskrit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID boardId;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = true, length = 200)
    private String description;

    @Column(nullable = false)
    private boolean closed = false;

    @Column
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<com.mangh.taskrit.model.List> lists = new ArrayList<>();
}
