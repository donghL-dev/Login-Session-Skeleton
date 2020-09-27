package com.donghun.loginskeleton.Account;

import com.donghun.loginskeleton.Account.dto.AccountDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getMain(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", user.getUsername());
        return "index";
    }

    @GetMapping("/sign-in")
    public String getSignIn() {
        return "account/sign-in";
    }

    @GetMapping("/sign-up")
    public String getSignUp() {
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @Valid AccountDTO accountDTO, Errors errors) {
        if (errors.hasErrors()) {
            if (errors.hasFieldErrors("email") && !errors.hasFieldErrors("password")) return new ResponseEntity<>("이메일 형식이 아닙니다.", HttpStatus.BAD_REQUEST);
            else if (errors.hasFieldErrors("password") && !errors.hasFieldErrors("email")) return new ResponseEntity<>("비밀번호를 8 ~ 20자 사이로 설정하세요.", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>("입력형식을 확인하세요.", HttpStatus.BAD_REQUEST);
        }

        accountRepository.save(accountDTO.toEntity(passwordEncoder.encode(accountDTO.getPassword())));
        return new ResponseEntity<>("{\"msg\" : \"회원가입 성공!\"}", HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String getTest() {
        return "test";
    }

}
