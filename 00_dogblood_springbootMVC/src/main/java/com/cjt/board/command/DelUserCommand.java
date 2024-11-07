package com.cjt.board.command;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class DelUserCommand {
    @NotNull(message = "삭제할 사용자 ID가 필요합니다.")
    private List<Integer> memberIds; // 사용자 ID 리스트
}
