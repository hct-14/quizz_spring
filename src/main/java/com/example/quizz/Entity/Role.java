package com.example.quizz.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "không được để trống name")
    private String name;
    @NotBlank(message = "không được để trống description")
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;  // Thay đổi từ User thành List<User>

//    @PrePersist
//    public void handleBeforeCreatedateAt() {
//        this.createdBy = SercuryUtil.getCurrentUserLogin().isPresent()==true ?
//                SercuryUtil.getCurrentUserLogin().get() : null;
//        this.createdAt = Instant.now();
//
//    }
//    @PreUpdate
//    public void handleBeforeUpdateAt() {
//        Optional<String> currentUserLogin = SercuryUtil.getCurrentUserLogin();
//        this.updatedBy = currentUserLogin.orElse(null);
//        this.updatedAt = Instant.now();
//    }


}
