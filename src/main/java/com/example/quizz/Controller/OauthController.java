package com.example.quizz.Config;

import com.example.quizz.Dto.Request.ReqLoginDTO;
import com.example.quizz.Dto.ResLoginDTO.ResLoginDTO;
import com.example.quizz.Dto.ResUSERDTO.UserCreateDTO;
import com.example.quizz.Entity.User;
import com.example.quizz.Exception.IdvalidException;
import com.example.quizz.Exception.annotation.Apimessage;
import com.example.quizz.Repository.UserRepository;
//import com.example.quizz.Service.AuthenticationService;
import com.example.quizz.Service.UserService;
import com.example.quizz.Util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@RestController
@RequestMapping("/api/v1/auth")
public class OauthController {
        @Autowired
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securyUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${hct_14.jwt.refresh-token-validity-in-seconds}")

    private long refreshTokenExpiration;
    @Autowired
    public OauthController(AuthenticationManagerBuilder authenticationManagerBuilder,
                          SecurityUtil securyUtil, UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securyUtil = securyUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDTO> login(@RequestBody ReqLoginDTO loginDto) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword());

        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

//        // set thông tin người dùng đăng nhập vào context (có thể sử dụng sau này)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(loginDto.getUsername());
        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                    currentUserDB.getId(),
                    currentUserDB.getEmail(),
                    currentUserDB.getName(),
                    currentUserDB.getRole());
            res.setUser(userLogin);
        }
        // create access token
        String access_token = this.securyUtil.createAccessToken(authentication.getName(), res);
        res.setAccessToken(access_token);


        // create refresh token
        String refresh_token = this.securyUtil.createRefreshToken(loginDto.getUsername(), res);

        // update user
        this.userService.updateUserToken(refresh_token, loginDto.getUsername());

        // set cookies
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }
//    @PostMapping("/auth/register")
//    @Apimessage("Create a new user")
//    public ResponseEntity<UserCreateDTO> createNewUser(@Valid @RequestBody User postManUser) throws IdvalidException{
//        boolean isEmailExist = this.userService.existsByEmail(postManUser.getEmail());
//        if (isEmailExist){
//            throw  new IdvalidException(
//                    "Email " + postManUser.getEmail() + " đã tồn tai rồi em, nhập email khác đi"
//            );
//        }
//
//        String hashPassword = passwordEncoder.encode(postManUser.getPassword());
//        postManUser.setPassword(hashPassword);
//        User createUser = this.userService.handleRegister(postManUser);
//        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.converToResCreateUserDTO(createUser));
//    }
    @GetMapping("/account")
    @Apimessage("fetch account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount() {
        String email = securyUtil.getCurrentUserLogin().isPresent()
                ? securyUtil.getCurrentUserLogin().get()
                : "";

        User currentUserDB = this.userService.handleGetUserByUsername(email);
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
        ResLoginDTO.UserGetAccount userGetAccount = new ResLoginDTO.UserGetAccount();

        if (currentUserDB != null) {
            userLogin.setId(currentUserDB.getId());
            userLogin.setEmail(currentUserDB.getEmail());
            userLogin.setName(currentUserDB.getName());
            userLogin.setRole(currentUserDB.getRole());

            userGetAccount.setUser(userLogin);
        }

        return ResponseEntity.ok().body(userGetAccount);
    }

    @GetMapping("/refresh")
    @Apimessage("Get User by refresh token")
    public ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name = "refresh_token", defaultValue = "abc") String refresh_token) throws IdvalidException {
        if (refresh_token.equals("abc")) {
            throw new IdvalidException("Bạn không có refresh token ở cookie");
        }
        // check valid
        Jwt decodedToken = this.securyUtil.checkValidRefreshToken(refresh_token);
        String email = decodedToken.getSubject();

        // check user by token + email
        User currentUser = this.userService.getUserByRefreshTokenAndEmail(refresh_token, email);
        if (currentUser == null) {
            throw new IdvalidException("Refresh Token không hợp lệ");
        }

        // issue new token/set refresh token as cookies
        ResLoginDTO res = new ResLoginDTO();
        User currentUserDB = this.userService.handleGetUserByUsername(email);
        if (currentUserDB != null) {
            ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin(
                    currentUserDB.getId(),
                    currentUserDB.getEmail(),
                    currentUserDB.getName(),
                    currentUserDB.getRole());
            res.setUser(userLogin);
        }

        // create access token
        String access_token = this.securyUtil.createAccessToken(email, res);
        res.setAccessToken(access_token);

        // create refresh token
        String new_refresh_token = this.securyUtil.createRefreshToken(email, res);

        // update user
        this.userService.updateUserToken(new_refresh_token, email);

        // set cookies
        ResponseCookie resCookies = ResponseCookie
                .from("refresh_token", new_refresh_token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, resCookies.toString())
                .body(res);
    }

    @PostMapping("/logout")
    @Apimessage("Logout User")
    public ResponseEntity<Void> logout() throws IdvalidException {
        String email = securyUtil.getCurrentUserLogin().isPresent() ? securyUtil.getCurrentUserLogin().get() : "";

        if (email.equals("")) {
            throw new IdvalidException("Access Token không hợp lệ");
        }

        // update refresh token = null
        this.userService.updateUserToken(null, email);

        // remove refresh token cookie
        ResponseCookie deleteSpringCookie = ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body(null);
    }
}

