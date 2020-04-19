package com.dhyoon.kakaoix.todo.api.models.repositories;

import com.dhyoon.kakaoix.todo.api.models.tables.Todo;
import com.dhyoon.kakaoix.todo.api.services.dto.ReferenceInfoDto;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoInfoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query(value = "SELECT t.* FROM todo t INNER JOIN todo_reference_info tr ON t.todo_id=tr.reference_todo_id WHERE tr.todo_id=?1 AND t.status='progress'",
            nativeQuery = true)
    public List<Todo> findProgressReferenceTodoByTodoId(Integer todoId);

    @Query(value = "SELECT t.* FROM todo t INNER JOIN todo_reference_info tr ON t.todo_id=tr.todo_id WHERE tr.reference_todo_id=?1 AND t.status='completed'",
            nativeQuery = true)
    public List<Todo> findCompletedReferenceTodoByTodoId(Integer todoId);

    public List<Todo> findByContentLikeAndStatusInOrderByRegDateDesc(String content, List<String> status);

    public List<Todo> findByContentLikeAndStatusInAndTodoIdNotInOrderByRegDateDesc(String content, List<String> status, List<Integer> todoId);

}
