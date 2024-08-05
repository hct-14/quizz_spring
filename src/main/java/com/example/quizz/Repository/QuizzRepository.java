package com.example.quizz.Repository;

import com.example.quizz.Entity.Quizz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuizzRepository extends JpaRepository<Quizz, Integer>, JpaSpecificationExecutor<Quizz> {
}
