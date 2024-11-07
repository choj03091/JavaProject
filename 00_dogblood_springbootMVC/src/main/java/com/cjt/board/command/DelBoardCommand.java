package com.cjt.board.command;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class DelBoardCommand {
    @NotEmpty(message = "최소 하나 체크하기")
    private List<Integer> boardIds; // 체크된 게시글의 ID를 담는 리스트
}
