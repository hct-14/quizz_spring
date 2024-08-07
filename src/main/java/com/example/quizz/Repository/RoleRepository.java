package com.example.quizz.Repository;

import com.example.quizz.Entity.Role;
import com.example.quizz.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
//    User existsByEmail(String email);
    boolean existsByName(String name);
}
