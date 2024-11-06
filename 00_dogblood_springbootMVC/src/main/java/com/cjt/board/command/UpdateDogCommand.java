package com.cjt.board.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDogCommand {

    @NotNull(message = "반려견 ID는 필수 항목입니다.")
    private Integer dogId;

    @NotBlank(message = "반려견 이름을 입력해주세요.")
    private String dogName;

    @NotBlank(message = "품종을 입력해주세요.")
    private String dogBreed;

    @NotNull(message = "나이를 입력해주세요.")
    private Integer dogAge;

    @NotNull(message = "몸무게를 입력해주세요.")
    private Double dogWeight;

    @NotBlank(message = "성별을 선택해주세요.")
    private String dogGender;

    @NotBlank(message = "약물 복용 여부를 선택해주세요.")
    private String dogDrug;

    @NotBlank(message = "혈액형을 선택해주세요.")
    private String dogBlood;
}
