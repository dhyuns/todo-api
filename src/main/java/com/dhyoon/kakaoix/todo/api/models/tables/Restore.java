package com.dhyoon.kakaoix.todo.api.models.tables;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="restore")
public class Restore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restoreId;
    private Integer todoId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date regDate;

    @PrePersist
    public void onCreate() {
        this.regDate = new Date();
    }

}
