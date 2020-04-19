package com.dhyoon.kakaoix.todo.api.services;

import com.dhyoon.kakaoix.todo.api.models.repositories.TodoReferenceInfoRepository;
import com.dhyoon.kakaoix.todo.api.models.repositories.TodoRepository;
import com.dhyoon.kakaoix.todo.api.models.tables.Todo;
import com.dhyoon.kakaoix.todo.api.models.tables.TodoReferenceInfo;
import com.dhyoon.kakaoix.todo.api.services.dto.ReferenceInfoDto;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoInfoDto;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoSearchDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoService {

    private TodoRepository todoRepository;

    private TodoReferenceInfoRepository todoReferenceInfoRepository;

    private TodoReferenceService todoReferenceService;

    public List<TodoInfoDto> getTodoList(TodoSearchDto input) {
        List<String> status = input.getStatus().stream().map(s -> {
            if(TodoStatus.PROGRESS.getStatusKor().equals(s)) {
                return TodoStatus.PROGRESS.getStatus();
            } else if(TodoStatus.COMPLETED.getStatusKor().equals(s)) {
                return TodoStatus.COMPLETED.getStatus();
            } else {
                return null;
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
        List<Todo> todoList;
        if(input.getTodoId() != null) {
            List<TodoReferenceInfo> referenceList = todoReferenceInfoRepository.findByTodoId(input.getTodoId());
            List<Integer> todoIdList = referenceList.stream().map(TodoReferenceInfo::getReferenceTodoId).collect(Collectors.toList());
            todoIdList.add(input.getTodoId());
            todoList = todoRepository.findByContentLikeAndStatusInAndTodoIdNotInOrderByRegDateDesc("%"+input.getKeyword()+"%", status, todoIdList);
        } else {
            todoList = todoRepository.findByContentLikeAndStatusInOrderByRegDateDesc("%"+input.getKeyword()+"%", status);
        }

        List<TodoInfoDto> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        for(Todo todo : todoList) {
            TodoInfoDto todoInfo = getTodoInfoDto(todo, sdf);
            if(TodoStatus.PROGRESS.getStatus().equals(todo.getStatus())) {
                todoInfo.setStatus(TodoStatus.PROGRESS.getStatusKor());
            } else if (TodoStatus.COMPLETED.getStatus().equals(todo.getStatus())) {
                todoInfo.setStatus(TodoStatus.COMPLETED.getStatusKor());
            }
            result.add(todoInfo);
        }
        return result;
    }

    public Todo saveTodo(TodoInfoDto input) {
        Todo todo;
        if(input.getTodoId() != null) {
            Optional<Todo> todoOpt = todoRepository.findById(input.getTodoId());
            if(!todoOpt.isPresent()) {
                throw new RuntimeException("해당 TODO가 존재하지 않습니다.");
            }
            todo = todoOpt.get();

            if(TodoStatus.COMPLETED.getStatus().equals(input.getStatus())) {
                //vali check
                List<Todo> referenceTodoList = todoRepository.findProgressReferenceTodoByTodoId(input.getTodoId());
                if(referenceTodoList.size() > 0) {
                    throw new RuntimeException("참조하고 있는 TODO 중 완료되지 않은 TODO가 존재합니다.");
                }
            } else {
                List<Todo> referenceTodoList = todoRepository.findCompletedReferenceTodoByTodoId(input.getTodoId());
                if(referenceTodoList.size() > 0) {
                    throw new RuntimeException("참조된 TODO 중 완료한 TODO가 존재합니다.");
                }
            }

            if(input.getContent() != null) {
                todo.setContent(input.getContent());
            }
            if(input.getStatus() != null) {
                todo.setStatus(input.getStatus());
            }

        } else {
            if(input.getContent() == null) {
                throw new RuntimeException("내용이 존재하지 않습니다.");
            }
            if(input.getStatus() == null) {
                throw new RuntimeException("상태값이 존재하지 않습니다.");
            }

            todo = new Todo();
            todo.setContent(input.getContent());
            todo.setStatus(input.getStatus());

        }
        Todo afterTodo = todoRepository.save(todo);
        input.setTodoId(afterTodo.getTodoId());
        todoReferenceService.addReference(input);
        return afterTodo;
    }

    public TodoInfoDto getTodoInfo(Integer todoId) {
        Optional<Todo> todoOpt = todoRepository.findById(todoId);
        if(!todoOpt.isPresent()) {
            throw new RuntimeException("해당 TODO가 존재하지 않습니다.");
        }
        Todo todo = todoOpt.get();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        TodoInfoDto todoInfo = getTodoInfoDto(todo, sdf);
        todoInfo.setStatus(todo.getStatus());
        return todoInfo;
    }

    private TodoInfoDto getTodoInfoDto(Todo todo, SimpleDateFormat sdf) {
        TodoInfoDto todoInfo = new TodoInfoDto();
        todoInfo.setTodoId(todo.getTodoId());
        todoInfo.setContent(todo.getContent());
        todoInfo.setRegDate(sdf.format(todo.getRegDate()));
        if(todo.getModifiedDate() != null) {
            todoInfo.setModifiedDate(sdf.format(todo.getModifiedDate()));
        }
        List<ReferenceInfoDto> referenceInfoDtoList = new ArrayList<>();
        for(TodoReferenceInfo reference: todo.getTodoReferenceInfo()) {
            Optional<Todo> referenceOpt = todoRepository.findById(reference.getReferenceTodoId());
            if(referenceOpt.isPresent()) {
                ReferenceInfoDto referenceInfoDto = ReferenceInfoDto.builder().todoReferenceInfoId(reference.getTodoReferenceInfoId())
                        .todoId(referenceOpt.get().getTodoId()).content(referenceOpt.get().getContent()).status(referenceOpt.get().getStatus()).build();
                referenceInfoDtoList.add(referenceInfoDto);
            }

        }
        todoInfo.setReferenceInfo(referenceInfoDtoList);
        return todoInfo;
    }

    public int removeTodo(List<Integer> todoIdList) {
        int result=1;
        for(Integer todoId : todoIdList) {
            Optional<Todo> todo = todoRepository.findById(todoId);
            if(!todo.isPresent()) {
                throw new RuntimeException("해당 TODO가 존재하지 않습니다.");
            }
            todoRepository.delete(todo.get());
            todoReferenceService.removeReferenceByTodoId(todo.get().getTodoId());
        }
        return result;
    }

    public Todo modifyStatus(TodoInfoDto input) {
        Optional<Todo> todoOpt = todoRepository.findById(input.getTodoId());
        if(!todoOpt.isPresent()) {
            throw new RuntimeException("해당 TODO가 존재하지 않습니다.");
        }
        Todo todo = todoOpt.get();

        if(TodoStatus.COMPLETED.getStatus().equals(input.getStatus())) {
            //vali check
            List<Todo> referenceTodoList = todoRepository.findProgressReferenceTodoByTodoId(input.getTodoId());
            if(referenceTodoList.size() > 0) {
                throw new RuntimeException("참조하고 있는 TODO 중 완료되지 않은 TODO가 존재합니다.");
            }
        } else {
            List<Todo> referenceTodoList = todoRepository.findCompletedReferenceTodoByTodoId(input.getTodoId());
            if(referenceTodoList.size() > 0) {
                throw new RuntimeException("참조된 TODO 중 완료한 TODO가 존재합니다.");
            }
        }

        todo.setStatus(input.getStatus());
        return todoRepository.save(todo);
    }


}
