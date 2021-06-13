package com.mangh.taskrit.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"list"})
public class Task {

    @Id
    @GeneratedValue
    private UUID taskId;

    @Column
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="description", columnDefinition="TEXT")
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User responsible;

    @Column(length = 3)
    private Integer effort;

    @Column(length = 2, precision = 3, scale = 1)
    private Double estimate;

    @Column(length = 2, precision = 3, scale = 1)
    private Double pending;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "listId", referencedColumnName = "listId")
    @JsonIgnore
    private List list;

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", responsible=" + responsible +
                ", effort=" + effort +
                ", estimate=" + estimate +
                ", pending=" + pending +
                ", list=" + list.getListId() +
                '}';
    }
}
