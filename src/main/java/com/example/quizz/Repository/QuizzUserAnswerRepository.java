package com.example.quizz.Repository;

import com.example.quizz.Entity.QuizzUserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuizzUserAnswerRepository extends JpaRepository<QuizzUserAnswer, Integer>, JpaSpecificationExecutor<QuizzUserAnswer> {
}
