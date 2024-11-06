package com.cjt.board.command;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class InsertUserCommand {
    
    @NotBlank(message = "회원 ID를 입력해주세요.")
    private String id; // 회원 ID
    
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password; // 비밀번호
    
    @NotBlank(message = "이름을 입력해주세요.")
    private String name; // 회원 이름
    
    @NotBlank(message = "주소를 입력해주세요.")
    private String address; // 주소
    
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email; // 이메일
    
    @NotBlank(message = "역할을 입력해주세요.")
    @Size(max = 20, message = "역할은 20자 이내여야 합니다.")
    private String role; // 역할
    
    private String delFlag = "N"; // 삭제 플래그, 기본값 'N'
    
    
    // 가입일은 자동으로 처리되므로 Command 객체에는 포함하지 않아도 됩니다.
}
