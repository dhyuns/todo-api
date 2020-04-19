package com.dhyoon.kakaoix.todo.api.models.tables;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name="todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer todoId;
    private String content;
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @OneToMany
    @JoinColumn(name="todo_id")
    private List<TodoReferenceInfo> todoReferenceInfo;

    @PrePersist
    public void onCreate() {
        this.regDate = new Date();
        this.modifiedDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedDate = new Date();
    }


}
