package com.cjt.board.command;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InsertBoardCommand {
	
    // 세션에서 설정할 예정
    private Integer memberId;
    
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
