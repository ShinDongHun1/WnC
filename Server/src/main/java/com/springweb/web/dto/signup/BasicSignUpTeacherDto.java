package com.springweb.web.dto.signup;

import com.springweb.web.domain.member.Role;
import com.springweb.web.domain.member.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Data
@AllArgsConstructor
public class BasicSignUpTeacherDto implements BasicSignUpDto{

    private String username;
    private String password;
    private String name;//직접 입력
    private int age; //나이
    private String career;//경력
    private List<MultipartFile> profileImg;//프사 URL


    //== 카카오 로그인이 아닐 경우 ==//
    private final boolean isKakaoMember=false;

    //== 일반 회원가입 기본 설정 ==//
    private final Role role = Role.BASIC;
    private final boolean activated=true;//활성화 여부

    @Override
    public Teacher toEntity(){
        return Teacher.builder()
                .username(username)
                .password(password)
                .name(name)
                .age(age)
                .isKakaoMember(isKakaoMember)
                .role(role)
                .activated(activated)
                .career(career)
                .build();
    }
}
