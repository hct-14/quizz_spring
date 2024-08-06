package com.example.quizz.Dto.ResUSERDTO;

import com.example.quizz.Enum.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.time.Instant;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDTO {

    private int id;
    private String name;
    private int age;
    private GenderEnum gender;
    private String email;
    private String password;
//    private Blob image;
    private String refreshToken;
    private Instant refreshExpireTime;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private RoleUser roleUser;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleUser{
        private int id;
        private String name;
    }

}
