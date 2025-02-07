package com.aenggyu.orderSystemApi.controller;


import com.aenggyu.orderSystemApi.SessionConst;
import com.aenggyu.orderSystemApi.domain.member.Member;
import com.aenggyu.orderSystemApi.dto.login.LoginRequest;
import com.aenggyu.orderSystemApi.dto.member.MemberResponse;
import com.aenggyu.orderSystemApi.exception.MemberException;
import com.aenggyu.orderSystemApi.service.login.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@Validated @RequestBody LoginRequest loginRequest,
                                                BindingResult bindingResult,
                                                HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new MemberException("유효성 검사 실패", HttpStatus.BAD_REQUEST);
        }

        Member member = loginService.login(loginRequest.getLoginId(), loginRequest.getPassword())
                .orElseThrow(() -> new MemberException("아이디 또는 비밀번호가 일치하지 않습니다.",
                        HttpStatus.BAD_REQUEST));

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        return ResponseEntity.ok(new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getGrade()
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.ok().build();
    }
}
