package com.dhyoon.kakaoix.todo.api.models.tables;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="todo_reference_info")
public class TodoReferenceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer todoReferenceInfoId;

    @Column(name="todo_id")
    private Integer todoId;

    private Integer referenceTodoId;

}
