package com.dhyoon.kakaoix.todo.api.services.dto;

import lombok.Data;

import java.util.List;

@Data
public class TodoSearchDto {

    private Integer todoId;
    private String keyword;
    private List<String> status;
}
