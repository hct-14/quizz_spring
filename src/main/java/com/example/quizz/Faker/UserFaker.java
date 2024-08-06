//package com.example.quizz;
//
//import com.example.quizz.Entity.Role;
//import com.example.quizz.Entity.User;
//import com.example.quizz.Enum.GenderEnum;
//import com.example.quizz.Repository.RoleRepository;
//import com.example.quizz.Repository.UserRepository;
//import com.github.javafaker.Faker;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.Instant;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;
//
//@Component
//public class UserFaker {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final Faker faker = new Faker();
//    private final Random random = new Random();
//
//    public UserFaker(UserRepository userRepository, RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//    public void generateData() {
//        List<Role> allRoles = generateRoles();
//        generateUsers(allRoles);
//    }
//
//    private List<Role> generateRoles() {
//        List<Role> allRoles = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Role role = Role.builder()
//                    .name(faker.job().title())
//                    .description(faker.lorem().sentence())
//                    .active(faker.bool().bool())
//                    .createdAt(Instant.now())
//                    .updatedAt(Instant.now())
//                    .build();
//
//            roleRepository.save(role);
//            allRoles.add(role);
//        }
//        return allRoles;
//    }
//
//    private void generateUsers(List<Role> allRoles) {
//        for (int i = 0; i < 10; i++) {
//            GenderEnum gender = getRandomGender();
//
//            User user = User.builder()
//                    .name(faker.name().fullName())
//                    .age(faker.number().numberBetween(18, 80))
//                    .gender(gender)
//                    .email(faker.internet().emailAddress())
//                    .password(faker.internet().password())
//                    .refreshToken(faker.internet().uuid())
//                    .roles(getRandomRoles(allRoles))
//                    .refreshExpireTime(faker.date().future(30, java.util.concurrent.TimeUnit.DAYS).toInstant())
//                    .createdAt(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS).toInstant())
//                    .updatedAt(faker.date().past(10, java.util.concurrent.TimeUnit.DAYS).toInstant())
//                    .deletedAt(faker.date().past(5, java.util.concurrent.TimeUnit.DAYS).toInstant())
//                    .build();
//
//            userRepository.save(user);
//        }
//    }
//
//    private GenderEnum getRandomGender() {
//        return GenderEnum.values()[random.nextInt(GenderEnum.values().length)];
//    }
//
//    private List<Role> getRandomRoles(List<Role> allRoles) {
//        int count = random.nextInt(allRoles.size()) + 1;
//        return allRoles.stream()
//                .sorted((r1, r2) -> random.nextInt(2) - 1)
//                .limit(count)
//                .collect(Collectors.toList());
//    }
//}
