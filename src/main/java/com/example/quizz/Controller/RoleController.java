package com.example.quizz.Controller;

import com.example.quizz.Entity.Role;
import com.example.quizz.Exception.IdvalidException;
import com.example.quizz.Exception.annotation.Apimessage;
import com.example.quizz.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("role")
    @Apimessage("Create linh rat xinhh")
    public ResponseEntity<Role> createRole(@RequestBody Role role) throws IdvalidException {
        return ResponseEntity.status(HttpStatus.OK).body(this.roleService.createRole(role));

    }
//    @GetMapping
//    public
}