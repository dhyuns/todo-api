package com.dhyoon.kakaoix.todo.api.webservices;

import com.dhyoon.kakaoix.todo.api.annotation.WebService;
import com.dhyoon.kakaoix.todo.api.models.repositories.TodoRepository;
import com.dhyoon.kakaoix.todo.api.models.tables.Todo;
import com.dhyoon.kakaoix.todo.api.models.tables.TodoReferenceInfo;
import com.dhyoon.kakaoix.todo.api.services.TodoService;
import com.dhyoon.kakaoix.todo.api.services.TodoStatus;
import com.dhyoon.kakaoix.todo.api.services.dto.ReferenceInfoDto;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoInfoDto;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoSearchDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.ref.Reference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@WebService("/api/todo")
@AllArgsConstructor
public class TodoWebService {

    private TodoService todoService;

    private TodoRepository todoRepository;

    @GetMapping("")
    public List<TodoInfoDto> getTodo(TodoSearchDto input) {
        return todoService.getTodoList(input);
    }

    @PostMapping("")
    @Transactional
    public Todo saveTodo(@RequestBody TodoInfoDto input) {
        return todoService.saveTodo(input);
    }

    @GetMapping("/detail")
    public TodoInfoDto getTodoInfo(@RequestParam Integer todoId) {
        return todoService.getTodoInfo(todoId);
    }

    @GetMapping("/remove")
    @Transactional
    public int removeTodo(@RequestParam List<Integer> todoIdList){
        return todoService.removeTodo(todoIdList);
    }

    @GetMapping("/status")
    @Transactional
    public Todo modifyStatus(TodoInfoDto input){
        return todoService.modifyStatus(input);
    }


}
