package com.example.quizz.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResQuizzUserDTO {

    private int id;
    private String name;
    private List<QuizzQuestion> quizzQuestions;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuizzQuestion {
        private int id;
        private String description;
        private Blob images;
        private List<Answer> answers; // Thay đổi thành List<Answer>

        @Setter
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Answer {
            private int id;
            private String description;
            private int isCorrect;
        }
    }
}
