package com.dhyoon.kakaoix.todo.api.models.repositories;

import com.dhyoon.kakaoix.todo.api.models.tables.Restore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestoreRepository extends JpaRepository<Restore, Integer> {
}
