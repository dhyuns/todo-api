package com.dhyoon.kakaoix.todo.api.models.repositories;

import com.dhyoon.kakaoix.todo.api.models.tables.Todo;
import com.dhyoon.kakaoix.todo.api.models.tables.TodoReferenceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoReferenceInfoRepository extends JpaRepository<TodoReferenceInfo, Integer> {

    public List<TodoReferenceInfo> findByTodoId(Integer todoId);

    public TodoReferenceInfo findByTodoIdAndReferenceTodoId(Integer todoId, Integer referenceTodoId);

}
