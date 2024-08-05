package com.example.quizz.Repository;

import com.example.quizz.Entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HistoryRepository extends JpaRepository<History, Integer>, JpaSpecificationExecutor<History> {
}
