package com.mangh.taskrit.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name="`User`")
public class User {

    @Id
    @GeneratedValue
    private UUID userId;

    @Column(length = 30, unique = true, nullable = false)
    private String username;

    @Column(length = 253, unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @Column(nullable = false)
    private UserRole role = UserRole.USER;

    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<BoardInfo> boards = new ArrayList<>();

    @Column
    private boolean enabled = true;
}
