package com.example.quizz.Repository;

import com.example.quizz.Entity.QuizzAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuizzAnswerRepository extends JpaRepository<QuizzAnswer, Integer>, JpaSpecificationExecutor<QuizzAnswer> {
}
