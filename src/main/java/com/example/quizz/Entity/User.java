package com.example.quizz.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "không được để trống name")
    private String name;
    @NotBlank(message = "không được để trống age")
    private int age;
    private String gender;
    @NotBlank(message = "không được để trống email")
    private String email;
    @NotBlank(message = "không được để trống password")
    private String password;
    private String image;
    private String refreshToken;
    private Instant refreshExpireTime;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    @OneToMany(mappedBy = "user")
    private List<History> history; // Thay đổi kiểu từ History thành List<History>

    @OneToMany(mappedBy = "user")
    private List<UserQuizz> userQuizz;

    @OneToMany(mappedBy = "user")
    private List<QuizzUserAnswer> quizzUserAnswer;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;  // Thay đổi từ Role thành List<Role>


}
