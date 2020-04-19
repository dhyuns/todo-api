package com.dhyoon.kakaoix.todo.api.services;

public enum TodoStatus {

    PROGRESS("progress", "진행중"),
    COMPLETED("completed", "완료");

    private String status;
    private String statusKor;

    TodoStatus(String status, String statusKor) {
        this.status = status;
        this.statusKor = statusKor;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusKor() {
        return statusKor;
    }
}
