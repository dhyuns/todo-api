package com.dhyoon.kakaoix.todo.api.webservices;

import com.dhyoon.kakaoix.todo.api.annotation.WebService;
import com.dhyoon.kakaoix.todo.api.models.tables.Todo;
import com.dhyoon.kakaoix.todo.api.services.TodoReferenceService;
import com.dhyoon.kakaoix.todo.api.services.dto.TodoInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@WebService("/api/reference")
@AllArgsConstructor
public class TodoReferenceWebService {

    private TodoReferenceService todoReferenceService;

    @PostMapping("/add")
    @Transactional
    public Todo addReference(@RequestBody TodoInfoDto input) {
        return todoReferenceService.addReference(input);
    }

    @GetMapping("/remove")
    @Transactional
    public int removeReference(@RequestParam Integer todoReferenceInfoId) {
        return todoReferenceService.removeReference(todoReferenceInfoId);
    }

}
