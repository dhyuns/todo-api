package com.dhyoon.kakaoix.todo.api.services;

import com.dhyoon.kakaoix.todo.api.models.repositories.TodoReferenceInfoRepository;
import com.dhyoon.kakaoix.todo.api.models.repositories.TodoRepository;
import com.dhyoon.kakaoix.todo.api.models.tables.Todo;
import com.dhyoon.kakaoix.todo.api.models.tables.TodoReferenceInfo;
import com.dhyoon.kakaoix.todo.api.services.dto.ReferenceInfoDto;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TodoReferenceService {

    private TodoRepository todoRepository;

    private TodoReferenceInfoRepository todoReferenceInfoRepository;

    public Todo addReference(TodoInfoDto input) {
        Optional<Todo> todoOpt = todoRepository.findById(input.getTodoId());
        if(!todoOpt.isPresent()) {
            throw new RuntimeException("해당 TODO가 존재하지 않습니다.");
        }
        Todo todo = todoOpt.get();

        List<TodoReferenceInfo> modelList = new ArrayList<>();

        if(input.getReferenceInfo() != null) {
            for(ReferenceInfoDto reference : input.getReferenceInfo()) {
                TodoReferenceInfo org = todoReferenceInfoRepository.findByTodoIdAndReferenceTodoId(input.getTodoId(), reference.getTodoId());
                if(org == null) {
                    TodoReferenceInfo model = new TodoReferenceInfo();
                    model.setTodoId(input.getTodoId());
                    model.setReferenceTodoId(reference.getTodoId());
                    modelList.add(model);
                }
            }

            if(modelList.size() > 0) {
                todoReferenceInfoRepository.saveAll(modelList);
            }
        }

        return todo;
    }

    public int removeReference(Integer todoReferenceInfoId) {
        int result = 1;
        Optional<TodoReferenceInfo> todoReferenceInfo = todoReferenceInfoRepository.findById(todoReferenceInfoId);
        if(!todoReferenceInfo.isPresent()) {
            throw new RuntimeException("해당 참조 TODO가 존재하지 않습니다.");
        }
        todoReferenceInfoRepository.delete(todoReferenceInfo.get());
        return result;
    }

    public int removeReferenceByTodoId(Integer todoId) {
        int result = 1;
        List<TodoReferenceInfo> todoReferenceInfoList = todoReferenceInfoRepository.findByTodoId(todoId);
        todoReferenceInfoRepository.deleteAll(todoReferenceInfoList);
        return result;
    }
}
