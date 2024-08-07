package com.example.quizz.Service;

import com.example.quizz.Dto.ResUserDTO;
import com.example.quizz.Dto.ResultPaginationDTO;
import com.example.quizz.Entity.History;
import com.example.quizz.Entity.QuizzUserAnswer;
import com.example.quizz.Entity.Role;
import com.example.quizz.Entity.User;
import com.example.quizz.Exception.IdvalidException;
import com.example.quizz.Repository.HistoryRepository;
import com.example.quizz.Repository.QuizzUserAnswerRepository;
import com.example.quizz.Repository.RoleRepository;
import com.example.quizz.Repository.UserRepository;
//import com.example.quizz.SercurityUtil;
import com.example.quizz.Util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HistoryRepository historyRepository;
    private final QuizzUserAnswerRepository quizzUserAnswerRepository;
    private final SecurityUtil securityUtil;
    private final PasswordEncoder passwordEncoder;
//    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       HistoryRepository historyRepository,
                       QuizzUserAnswerRepository quizzUserAnswerRepository,
                       SecurityUtil securityUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.historyRepository = historyRepository;
        this.quizzUserAnswerRepository = quizzUserAnswerRepository;
        this.securityUtil = securityUtil;
        this.passwordEncoder = passwordEncoder;
    }



    public boolean exitsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }
    public User createUser(User user) {

//        if (user.getCompany() !=null){
//            Optional<Company> companyOptional = this.companyService.findById(user.getCompany().getId());
//            user.setCompany(companyOptional.isPresent() ? companyOptional.get() : null);
//
//        }
        if (user.getRole() != null){
            Optional<Role> role = this.roleRepository.findById(user.getRole().getId());
            user.setRole(role.isPresent() ? role.get() : null);
        }

        return this.userRepository.save(user);

    }
//    public User createUser(User user) throws IdvalidException {
//        Optional<User> userExists = findByEmail(user.getEmail());
//        if (!userExists.isPresent()) {
//
//            return this.userRepository.save(user);
//        } else {
//          throw new IdvalidException("email này đã tồn tại vui lòng nhập email khác");
//        }
//    }
    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
    public void updateUserToken(String token, String email){
        User currentUser = this.handleGetUserByUsername(email);
        if(currentUser != null){
            currentUser.setRefreshToken(token);
            this.userRepository.save(currentUser);
        }
    }
    public User getUserByRefreshTokenAndEmail(String token, String email)throws IdvalidException {
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    public Optional<User> getUserById(int id) throws IdvalidException {
        return this.userRepository.findById(id);

    }

    public ResultPaginationDTO findbyAllUser(Specification<User> spec, Pageable pageable){
        Page<User> userPage = this.userRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();
        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(userPage.getTotalPages());
        mt.setTotal(userPage.getTotalElements());
        rs.setMeta(mt);

        List<ResUserDTO> listUser = userPage.getContent()
                .stream().map(item -> new ResUserDTO(
                item.getId(),
                item.getName(),
                item.getAge(),
                item.getGender(),
                item.getEmail(),
                item.getCreatedAt(),
                item.getUpdatedAt(),
                item.getDeletedAt(),
                        item.getHistory() != null ? item.getHistory().stream().map(history -> new ResUserDTO.History(
                                history.getId(),
                                history.getTotalCorrect(),
                                history.getTotalQuestions()
                        )).collect(Collectors.toList()) : new ArrayList<>(),
                        item.getQuizzUserAnswer() != null ? item.getQuizzUserAnswer().stream().map(userAnswer -> new ResUserDTO.QuizzUserAnswer(
                                userAnswer.getId()
                        )).collect(Collectors.toList()) : new ArrayList<>(),
                        new ResUserDTO.RoleUser(
                                item.getRole() != null ? item.getRole().getId() : 0,
                                item.getRole() != null ? item.getRole().getName() : null
                        )
        )).collect(Collectors.toList());

        rs.setResult(listUser);
        return rs;
    }
    public User handleUpdateUser(User userFe, User userBe) throws IdvalidException{
        Optional<User> userOptional = this.userRepository.findById(userFe.getId());
        if (userOptional.isPresent()){
            if (userFe.getAge() >0) {
                userBe.setAge(userFe.getAge());
            }
            if (userFe.getEmail() != null){
                userBe.setEmail(userFe.getEmail());
            }
            if (userFe.getGender() != null){
                userBe.setGender(userBe.getGender());
            }
            if (userFe.getName() !=null){
                userBe.setName(userFe.getName());
            }
            if (userFe.getRole() != null){
                Role role =userFe.getRole();
                if (role.getId()>0){
                    Optional<Role> roleCheck = this.roleRepository.findById(role.getId());
                    if (roleCheck.isPresent()){
                        userBe.setRole(roleCheck.get());
                    }else {
                        userBe.setRole(null);
                    }
                } else {
                    // Nếu không có công ty, đặt giá trị null
                    userBe.setRole(null);
                }
            }
            }else {
            throw new IdvalidException("người dùng không tồn tại");
        }

        return this.userRepository.save(userBe);

    }

    public ResUserDTO convertToResUserDTO(User user) {
        ResUserDTO res = new ResUserDTO();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setEmail(user.getEmail());
        res.setCreatedAt(user.getCreatedAt());
        res.setUpdatedAt(user.getUpdatedAt());
        res.setDeletedAt(user.getDeletedAt());

        // Xử lý history
        if (user.getHistory() != null) {
            List<ResUserDTO.History> historyList = user.getHistory().stream()
                    .map(history -> new ResUserDTO.History(
                            history.getId(),
                            history.getTotalCorrect(),
                            history.getTotalQuestions()
                    ))
                    .collect(Collectors.toList());
            res.setHistory(historyList);
        }

        // Xử lý roleUser
        if (user.getRole() != null) {
            ResUserDTO.RoleUser roleUser = new ResUserDTO.RoleUser();
            roleUser.setId(user.getRole().getId());
            roleUser.setName(user.getRole().getName());
            res.setRoleUser(roleUser);
        }

        // Xử lý quizzUserAnswer
        if (user.getQuizzUserAnswer() != null) {
            List<ResUserDTO.QuizzUserAnswer> quizzUserAnswerList = user.getQuizzUserAnswer().stream()
                    .map(quizzUserAnswer -> new ResUserDTO.QuizzUserAnswer(
                            quizzUserAnswer.getId()
                            // Thêm các thuộc tính nếu cần
                    ))
                    .collect(Collectors.toList());
            res.setQuizzUserAnswer(quizzUserAnswerList);
        }

        return res;
    }

    public void deleteUser(int id) throws IdvalidException {
        Optional<User> userOptional = this.getUserById(id);
        if (!userOptional.isPresent()){
            throw new IdvalidException("người dùng không tồn tại");

        }
       User user = userOptional.get();
        if (user.getRole() != null){
            user.setRole(null);
            this.userRepository.save(user);
        }
        if (user.getHistory() != null){
            for (History history : user.getHistory()){
                this.historyRepository.delete(history);
            }
        }
//        if (user.get)
        if (user.getQuizzUserAnswer() != null){
            for (QuizzUserAnswer quizzUserAnswer : user.getQuizzUserAnswer()){
                this.quizzUserAnswerRepository.delete(quizzUserAnswer);
            }
        }

        this.userRepository.deleteById(id);

    }

    public User notifyPassword(String current_password, String new_password) throws IdvalidException {
        Optional<String> currentUserOpt = securityUtil.getCurrentUserLogin();

        if (!currentUserOpt.isPresent()) {
            throw new IdvalidException("Không có người dùng hiện tại");
        }

        String email = currentUserOpt.get();

        // Tìm người dùng bằng email
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));

        if (!userOptional.isPresent()) {
            throw new IdvalidException("Người dùng không tồn tại");
        }

        User user = userOptional.get();

        // So sánh mật khẩu hiện tại với mật khẩu trong cơ sở dữ liệu
        if (!passwordEncoder.matches(current_password, user.getPassword())) {
            throw new IdvalidException("Mật khẩu hiện tại không đúng");
        }

        user.setPassword(passwordEncoder.encode(new_password));
        return this.userRepository.save(user);
    }


}
