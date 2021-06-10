package com.mangh.taskrit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue
    private UUID taskId;

    @Column
    private String title;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User responsible;

    @Column(length = 3)
    private Integer effort;

    @Column(length = 2)
    private Integer estimate;

    @Column(length = 2)
    private Integer pending;

    @ManyToOne
    @JoinColumn(name = "listId")
    private List list;
}
