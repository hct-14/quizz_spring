package com.example.quizz.Repository;

import com.example.quizz.Entity.QuizzUser;
import com.example.quizz.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuizzUserRepository extends JpaRepository<QuizzUser, Integer>, JpaSpecificationExecutor<QuizzUser> {

    @Query("SELECT CASE WHEN COUNT(q) > 0 THEN true ELSE false END FROM QuizzUser q WHERE q.user.id = :userId AND q.quizz.id = :quizzId")
    boolean existsByUserAndQuizz(@Param("userId") int userId, @Param("quizzId") int quizzId);

}
