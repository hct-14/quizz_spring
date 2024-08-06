package com.example.quizz.Dto;

import com.example.quizz.Enum.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResUserDTO {

    private int id;
    private String name;
    private int age;
    private GenderEnum gender;
    private String email;
    private Instant refreshExpireTime;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
    private List<History> history;
    private List<QuizzUserAnswer> quizzUserAnswer;
    private RoleUser roleUser;

    public <R> ResUserDTO(int id, String name, int age, GenderEnum gender, String email, Instant createdAt, Instant updatedAt, Instant deletedAt, R collect, R collect1, RoleUser roleUser) {
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class History {
        private int id;
        private int totalCorrect;
        private int totalQuestions;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class QuizzUserAnswer {
        private int id;
        // private String description; // Nếu cần thêm, hãy bỏ comment này
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class RoleUser {
        private int id;
        private String name;
    }
}
