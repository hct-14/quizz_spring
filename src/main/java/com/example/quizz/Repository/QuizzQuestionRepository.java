package com.example.quizz.Repository;

import com.example.quizz.Entity.QuizzQuetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuizzQuestionRepository extends JpaRepository<QuizzQuetion, Integer>, JpaSpecificationExecutor<QuizzQuetion> {
    List<QuizzQuetion> findByIdIn(List<Integer> id);

}
