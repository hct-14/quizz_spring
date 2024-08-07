package com.example.quizz.Controller;


import com.example.quizz.Dto.ResUserDTO;
import com.example.quizz.Entity.User;
import com.example.quizz.Exception.IdvalidException;
import com.example.quizz.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;



    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String huhu(){
        return "hoang cong thanh dep trai";
    }
    @PostMapping("/user")
    public User test(@Valid @RequestBody User user) throws IdvalidException {
        String hashPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);
        User createUser = this.userService.createUser(user);
       return createUser;

    }
    @GetMapping("/user/{id}")
    public ResponseEntity<ResUserDTO> fectchById(@PathVariable int id) throws IdvalidException {
        Optional<User> userOptional = this.userService.getUserById(id);
        if (!userOptional.isPresent()){
            throw new IdvalidException("người dùng không tồn tại");
        }
        User userSave = userOptional.get() ;
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(userSave));

    }

//    @GetMapping("/user")
//    public ResponseEntity<ResultPaginationDTO> getAllUser(@Filter @Valid Specification<User> spec, Pageable pageable) {
////        Specification<User> spec = buildSpecificationFromParams(params);
//        return ResponseEntity.status(HttpStatus.OK).body(this.userService.findbyAllUser(spec, pageable));
//    }

//    private Specification<User> buildSpecificationFromParams(Map<String, String> params) {
//        return (root, query, criteriaBuilder) -> {
//            Predicate predicate = criteriaBuilder.conjunction();
////            if (params.containsKey("name")) {
////                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("name"), params.get("name")));
////            }
////            if (params.containsKey("age")) {
////                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("age"), Integer.parseInt(params.get("age"))));
////            }
////             Thêm các điều kiện khác nếu cần
//            return predicate;
//        };
//    }


   @PutMapping("/user")
    public ResponseEntity<ResUserDTO> updateUser(@RequestBody User user) throws IdvalidException {
       Optional<User> ericUser = this.userService.getUserById(user.getId());
       if (!ericUser.isPresent()) {
           throw new IdvalidException("User với id = " + user.getId() + " không tồn tại");
       }
       User userSave = this.userService.handleUpdateUser(user, ericUser.get());
       return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(userSave));
   }

   @DeleteMapping("/user/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable @Valid int id) throws IdvalidException {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("xoa oke");

   }
   @PostMapping("/user-change")
    public ResponseEntity<String> changeUser(@RequestBody String current_password, String new_password, User user) throws IdvalidException {
        this.userService.notifyPassword(current_password, new_password);
        return ResponseEntity.status(HttpStatus.OK).body("doi mat khau oke");
   }
}
