package com.example.quizz.Entity;

import com.example.quizz.Enum.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "không được để trống name")
    private String name;
    @NotNull
    private int age;

    private GenderEnum gender; // Thay đổi kiểu dữ liệu thành GenderEnum
    @NotBlank(message = "không được để trống email")
    private String email;
    @NotBlank(message = "không được để trống password")
    private String password;

    //    @Lob // Thêm annotation này
    @Column(length = 5000)  // Thay đổi length theo nhu cầu
    private Blob image; // Thay đổi kiểu dữ liệu thành byte[]
    @Column(length = 5000)  // Thay đổi length theo nhu cầu

    private String refreshToken;
    private Instant refreshExpireTime;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    @OneToMany(mappedBy = "user")
    private List<History> history;


    @OneToMany(mappedBy = "user")
    private List<QuizzUserAnswer> quizzUserAnswer;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
