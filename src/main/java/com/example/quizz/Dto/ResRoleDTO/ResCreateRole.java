package com.example.quizz.Dto.ResRoleDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResCreateRole {

    private int id;
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private String createdBy;
    private String updatedBy;
    private List<UserRole> userRole;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserRole {
        private int id;
        private String name;
    }
}
