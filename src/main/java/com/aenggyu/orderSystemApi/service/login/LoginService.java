package com.aenggyu.orderSystemApi.service.login;

import com.aenggyu.orderSystemApi.domain.member.Member;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(String loginId, String password);
}
