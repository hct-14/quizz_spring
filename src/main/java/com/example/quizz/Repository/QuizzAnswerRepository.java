package com.example.quizz.Repository;

import com.example.quizz.Entity.QuizzAnswer;
import com.example.quizz.Entity.QuizzQuetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuizzAnswerRepository extends JpaRepository<QuizzAnswer, Integer>, JpaSpecificationExecutor<QuizzAnswer> {
    List<QuizzAnswer> findByIdIn(List<Integer> id);

}
