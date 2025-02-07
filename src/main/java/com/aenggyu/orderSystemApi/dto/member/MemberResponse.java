package com.aenggyu.orderSystemApi.dto.member;

import com.aenggyu.orderSystemApi.domain.Grade;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MemberResponse {

    private Long id;
    private String loginId;
    private String name;
    private Grade grade;
}
