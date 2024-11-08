package com.cjt.board.command;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DelOneBoardCommand {
    @NotNull(message = "삭제할 게시물 ID가 필요합니다.")
    private Integer boardId; // 삭제할 게시물 ID
}
