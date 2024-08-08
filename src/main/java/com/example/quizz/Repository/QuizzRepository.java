package com.example.quizz.Repository;

import com.example.quizz.Entity.Quizz;
import com.example.quizz.Entity.QuizzQuetion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QuizzRepository extends JpaRepository<Quizz, Integer>, JpaSpecificationExecutor<Quizz> {
    List<QuizzQuetion> findByIdIn(List<Integer> id);

}
