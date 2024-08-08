package com.example.quizz.Entity;

import com.example.quizz.Util.SecurityUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "quizzquestion")
public// Corrected table name
class QuizzQuetion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private Blob image;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "quizzQuestion")
    private List<QuizzAnswer> quizzAnswers;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quizz quizz;

    @PrePersist
    public void handleBeforeCreatedateAt() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().orElse(null);
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdateAt() {
        this.updatedBy = SecurityUtil.getCurrentUserLogin().orElse(null);
        this.updatedAt = Instant.now();
    }

    public void setQuizzAnswers(List<QuizzAnswer> dbQuizzAnswers) {

    }
}
