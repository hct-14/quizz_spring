package com.example.quizz.Repository;

import com.example.quizz.Entity.QuizzQuetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserQuestionRepository extends JpaRepository<QuizzQuetion, Integer>, JpaSpecificationExecutor<QuizzQuetion> {
}
