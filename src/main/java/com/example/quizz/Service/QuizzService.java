package com.example.quizz.Service;

import com.example.quizz.Dto.ResQuizzDTO.ResQuizzDTO;
import com.example.quizz.Dto.ResQuizzUserDTO;
import com.example.quizz.Dto.ResultPaginationDTO;
import com.example.quizz.Entity.*;
import com.example.quizz.Exception.IdvalidException;
import com.example.quizz.Repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuizzService {
    private final QuizzRepository quizzRepository;
    private final HistoryRepository historyRepository;
    private final QuizzUserAnswerRepository quizzUserAnswerRepository;
    private final QuizzQuestionRepository quizzQuestionRepository;
    private final UserRepository userRepository;

    public QuizzService(QuizzRepository quizzRepository,UserRepository userRepository, HistoryRepository historyRepository, QuizzUserAnswerRepository quizzUserAnswerRepository, QuizzQuestionRepository quizzQuestionRepository) {
        this.quizzRepository = quizzRepository;
        this.historyRepository = historyRepository;
        this.quizzUserAnswerRepository = quizzUserAnswerRepository;
        this.quizzQuestionRepository = quizzQuestionRepository;
        this.userRepository = userRepository;
    }

    public Quizz createQuizz(Quizz quizz){
    return this.quizzRepository.save(quizz);
    }

    public Quizz findById(int id){
        return this.findById(id);
    }
    public ResultPaginationDTO findByAll(Specification<Quizz> spec, Pageable pageable) {
        Page<Quizz> quizzes = this.quizzRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(quizzes.getTotalPages());
        mt.setTotal(quizzes.getTotalElements());
        rs.setMeta(mt);

        List<ResQuizzDTO> listQuizz = quizzes.getContent().stream().map(item -> new ResQuizzDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getDifficulty(),

                item.getHistory().stream().map(history -> new ResQuizzDTO.History(
                        history.getId(),
                        history.getTotalQuestions(),
                        history.getTotalCorrect()
                )).collect(Collectors.toList()),

                item.getQuizzQuetion().stream().map(question -> new ResQuizzDTO.QuizzQuetion(
                        question.getId(),
                        question.getDescription()
                )).collect(Collectors.toList()),

                item.getQuizzUserAnswer().stream().map(answer -> new ResQuizzDTO.QuizzUserAnswer(
                        answer.getId()
                )).collect(Collectors.toList())
        )).collect(Collectors.toList());

        rs.setResult(listQuizz);

        return rs;
    }

    public Quizz updateQuizz(Quizz quizzFe, Quizz quizzBe) throws IdvalidException {
        Optional<Quizz> quizzOptional = this.quizzRepository.findById(quizzFe.getId());
        if (!quizzOptional.isPresent()){
            throw new IdvalidException("Không có quizz này em ơi");
        }
        if (quizzFe.getDescription()!=null){
            quizzBe.setDescription(quizzFe.getDescription());
        }
        if (quizzFe.getDifficulty() !=null){
            quizzBe.setDifficulty(quizzFe.getDifficulty());
        }
        if (quizzFe.getName()!=null){
            quizzBe.setName(quizzFe.getName());
        }
        if (quizzFe.getImage()!=null){
            quizzBe.setImage(quizzFe.getImage());
        }
        return  this.quizzRepository.save(quizzBe);
    }

    public void deleteById(int id) throws IdvalidException {
        Optional<Quizz> quizzOptional = this.quizzRepository.findById(id);
        if (!quizzOptional.isPresent()){
            throw new IdvalidException("quizz không tồn tại");
        }

        if (quizzOptional.get().getHistory()!=null) {
            for (History history : quizzOptional.get().getHistory()) {
                this.historyRepository.delete(history);
            }
        }

        if (quizzOptional.get().getQuizzQuetion()!=null){
            for (QuizzQuetion quizzQuetion : quizzOptional.get().getQuizzQuetion()){
                this.quizzQuestionRepository.delete(quizzQuetion);
            }
        }
        if (quizzOptional.get().getQuizzUserAnswer() !=null){
            for (QuizzUserAnswer quizzUserAnswer : quizzOptional.get().getQuizzUserAnswer()){
                this.quizzUserAnswerRepository.delete(quizzUserAnswer);
            }
        }
        this.quizzRepository.deleteById(id);

    }
//    public ResultPaginationDTO getQuizzUser(Specification<Quizz> spec, Pageable pageable) {
//        Page<Quizz> quizzPage = this.quizzRepository.findAll(spec, pageable);
//        ResultPaginationDTO rs = new ResultPaginationDTO();
//        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
//
//        mt.setPage(pageable.getPageNumber() + 1);
//        mt.setPageSize(pageable.getPageSize());
//        mt.setPages(quizzPage.getTotalPages());
//        mt.setTotal(quizzPage.getTotalElements());
//        rs.setMeta(mt);
//
//        List<ResQuizzUserDTO> listUser = quizzPage.getContent()
//                .stream()
//                .map(item -> new ResQuizzUserDTO(
//                        item.getId(),
//                        item.getName(),
//
//                        item.getQuizzQuetion() != null ? item.getQuizzQuetion().stream().map(quizzQuestion -> new ResQuizzUserDTO.QuizzQuestion(
//                                quizzQuestion.getId(),
//                                quizzQuestion.getDescription(),
//                                quizzQuestion.getImage(),
//
//                                quizzQuestion.getAnswer() != null ? quizzQuestion.getA.stream().map(answer -> new ResQuizzUserDTO.QuizzQuestion.Answer(
//                                        answer.getId(),
//                                        answer.getDescription(),
//                                        answer.getIsCorrect()
//                                )).collect(Collectors.toList()) : new ArrayList<>()
//                        )).collect(Collectors.toList()) : new ArrayList<>(),
//
////                        item.getQuizzAnswers() != null ? item.getQuizzAnswers().stream().map(answer -> new ResQuizzUserDTO.QuizzQuestion.Answer(
////                                answer.getId(),
////                                answer.getDescription(),
////                                answer.getIsCorrect()
////                        )).collect(Collectors.toList()) : new ArrayList<>()
//                ))
//                .collect(Collectors.toList());
//
//        rs.setContent(listUser);
//
//        return rs;
//    }




}

