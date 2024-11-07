package com.cjt.board.command;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DelDogCommand {
	@NotNull(message = "삭제할 강아지를 골라주세요.")
	private List<Integer> dogIds;

}
