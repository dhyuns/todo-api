package com.dhyoon.kakaoix.todo.api.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReferenceInfoDto {

    private Integer todoReferenceInfoId;
    private Integer todoId;
    private String content;
    private String status;

    @Builder
    public ReferenceInfoDto(Integer todoReferenceInfoId, Integer todoId, String content, String status) {
        this.todoReferenceInfoId = todoReferenceInfoId;
        this.todoId = todoId;
        this.content = content;
        this.status = status;
    }
}
