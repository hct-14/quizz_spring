package com.example.quizz.Dto.ResQuizzDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResQuizzDTO {

    private int id;


    private String name;

    private String description;

    private String difficulty;

    private List<History> history;

    private List<QuizzQuetion> quizzQuetion;

    private List<QuizzUserAnswer> quizzUserAnswer;
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class History{
        private int id;
        private int totalQuestions;
        private int totalCorrect;
    }
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuizzQuetion{
        private int id;
        private String description;

    }
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuizzUserAnswer{
        private int id;

    }

}
