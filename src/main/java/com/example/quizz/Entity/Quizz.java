package com.example.quizz.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Blob;
import java.time.Instant;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "quizz")
public class Quizz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "không được để trống name")
    private String name;

    @NotBlank(message = "không được để trống desc")
    private String description;

//    @Lob // Thêm annotation này
    @Column(length = 5000)
    private Blob image;
    private String difficulty;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "quizz")
    private List<History> history;


    @OneToMany(mappedBy = "quizz")
    private List<QuizzUserAnswer> quizzUserAnswer;
}
