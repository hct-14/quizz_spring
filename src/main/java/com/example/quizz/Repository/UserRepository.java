package com.example.quizz.Repository;

import com.example.quizz.Entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer>, JpaSpecificationExecutor<User> {
    boolean existsByEmail(String email);
    User findByRefreshTokenAndEmail(String token, String email);
    User findByEmail(String email);

}
