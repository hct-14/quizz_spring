package com.example.quizz.Dto.ResRoleDTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResRoleDTO {

    private int id;
    private String name;
    private String description;
    private boolean active;
//    private Instant createdAt;
//    private Instant updatedAt;
//    private String createdBy;
//    private String updatedBy;
}
