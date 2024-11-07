package com.cjt.board.command;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class DelUserCommand {
    @NotEmpty(message = "삭제할 사용자 ID가 필요합니다.")
    private List<Integer> memberIds; // 삭제할 사용자 ID 리스트
}
