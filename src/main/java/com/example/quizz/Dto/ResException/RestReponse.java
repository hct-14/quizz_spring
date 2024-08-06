package com.example.quizz.Dto.ResException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestReponse<T>{
    public int statusCode;
    private  String error;
    private Object message;
    private T data;
}
