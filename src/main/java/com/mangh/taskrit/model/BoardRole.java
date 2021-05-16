package com.mangh.taskrit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class BoardRole {

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Id
    @GeneratedValue
    private UUID boardRoleId;

    @OneToOne
    @JoinColumn(name = "boardId", nullable = false)
    private Board board;

    @Column(nullable = false)
    private BoardRoleType roleType = BoardRoleType.EDITOR;
}
