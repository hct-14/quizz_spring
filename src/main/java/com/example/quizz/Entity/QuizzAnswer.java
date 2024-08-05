package com.example.quizz.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "quizzanswer")
public class QuizzAnswer {
    @Id
    private int id;
    private String description;
    private String correctAnswer;

    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @ManyToOne
    @JoinColumn(name = "quizz_id")
    private QuizzQuetion quizzQuetion;
}
