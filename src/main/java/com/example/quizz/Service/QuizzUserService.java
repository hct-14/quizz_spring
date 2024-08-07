//package com.example.quizz.Service;
//
//import com.example.quizz.Dto.ResQuizzUserDTO.ResQuizzUserDTO;
//import com.example.quizz.Dto.ResUserDTO;
//import com.example.quizz.Dto.ResultPaginationDTO;
//import com.example.quizz.Entity.QuizzUser;
//import com.example.quizz.Entity.User;
//import com.example.quizz.Exception.IdvalidException;
//import com.example.quizz.Repository.QuizzUserRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class QuizzUserService {
//    private final QuizzUserRepository quizzUserRepository;
//
//    public QuizzUserService(QuizzUserRepository quizzUserRepository) {
//        this.quizzUserRepository = quizzUserRepository;
//    }
//
//    public QuizzUser createQuizzUser(QuizzUser quizzUser) throws IdvalidException {
//        boolean exists = quizzUserRepository.existsByUserAndQuizz(quizzUser.getUser().getId(), quizzUser.getQuizz().getId());
//        if (exists) {
//            throw new IdvalidException("đã tồn tại rồi");
//        }
//        return this.quizzUserRepository.save(quizzUser);
//    }
////    public ResultPaginationDTO getQuizzUser(Specification<QuizzUser> spec, Pageable pageable){
////        Page<QuizzUser> userPage = this.quizzUserRepository.findAll(spec, pageable);
////        ResultPaginationDTO rs = new ResultPaginationDTO();
////        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
////
////        mt.setPage(pageable.getPageNumber()+1);
////        mt.setPageSize(pageable.getPageSize());
////        mt.setPages(userPage.getTotalPages());
////        mt.setTotal(userPage.getTotalElements());
////        rs.setMeta(mt);
////
////        List<ResQuizzUserDTO> listUser = userPage.getContent()
////                .stream().map(item -> new ResQuizzUserDTO(
////                        item.getId(),
////
////
////                        item.getQuizz() != null ? item.getQuizz().stream().map(history -> new ResUserDTO.History(
////                                history.getId(),
////                                history.getTotalCorrect(),
////                                history.getTotalQuestions()
////                        )).collect(Collectors.toList()) : new ArrayList<>(),
//////                        item.getQuizzUserAnswer() != null ? item.getQuizzUserAnswer().stream().map(userAnswer -> new ResUserDTO.QuizzUserAnswer(
//////                                userAnswer.getId()
//////                        )).collect(Collectors.toList()) : new ArrayList<>(),
//////                        new ResUserDTO.RoleUser(
//////                                item.getRole() != null ? item.getRole().getId() : 0,
//////                                item.getRole() != null ? item.getRole().getName() : null
//////                        )
////                )).collect(Collectors.toList());
////
////
////    }
//}
