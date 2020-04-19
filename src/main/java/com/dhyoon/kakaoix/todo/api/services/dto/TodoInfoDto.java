package com.dhyoon.kakaoix.todo.api.services.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TodoInfoDto {

    private Integer todoId;
    private String content;
    private String status;
    private String regDate;
    private String modifiedDate;
    private List<ReferenceInfoDto> referenceInfo;

}
