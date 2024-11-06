package com.cjt.board.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateUserCommand {
	
	@NotNull(message = "회원 ID는 필수 항목입니다.")
	private Integer memberId;

    @NotBlank(message = "주소를 입력하세요.")
    private String address; // 주소

    @NotBlank(message = "이메일을 입력하세요.")
    private String email; // 이메일

    @NotBlank(message = "역할을 선택하세요.")
    private String role; 

    private String delFlag; 

}
