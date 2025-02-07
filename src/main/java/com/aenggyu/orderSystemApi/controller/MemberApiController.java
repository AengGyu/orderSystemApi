package com.aenggyu.orderSystemApi.controller;

import com.aenggyu.orderSystemApi.domain.Grade;
import com.aenggyu.orderSystemApi.domain.member.Member;
import com.aenggyu.orderSystemApi.dto.member.MemberRegisterRequest;
import com.aenggyu.orderSystemApi.dto.member.MemberResponse;
import com.aenggyu.orderSystemApi.exception.MemberException;
import com.aenggyu.orderSystemApi.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@Validated @RequestBody MemberRegisterRequest request,
                                                   BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new MemberException("유효성 검사 실패", HttpStatus.BAD_REQUEST);
        }

        if (memberService.findMemberByLoginId(request.getLoginId()).isPresent()) {
            throw new MemberException("이미 존재하는 아이디입니다.", HttpStatus.BAD_REQUEST);
        }

        Member member = new Member(request.getLoginId(), request.getPassword(), request.getName());
        member.setGrade(Grade.BRONZE);

        Member registeredMember = memberService.register(member);

        return ResponseEntity.ok(new MemberResponse(
                registeredMember.getId(),
                registeredMember.getLoginId(),
                registeredMember.getName(),
                registeredMember.getGrade()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> member(@PathVariable Long id) {
        Member member = memberService.findMemberById(id).
                orElseThrow(() -> new MemberException("회원이 존재하지 않습니다.", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok(new MemberResponse(
                member.getId(),
                member.getLoginId(),
                member.getName(),
                member.getGrade()
        ));
    }
}
