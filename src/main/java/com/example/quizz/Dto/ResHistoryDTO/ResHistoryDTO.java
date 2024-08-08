package com.example.quizz.Dto.ResHistoryDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResHistoryDTO {

    private int id;
    private int totalQuestions;
    private int totalCorrect;
    private QuizzHis quizz;
    private UserHis user;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class  QuizzHis{
        private int id;

        private String name;

        private String description;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class  UserHis{
        private int id;
        private String name;
    }


}
