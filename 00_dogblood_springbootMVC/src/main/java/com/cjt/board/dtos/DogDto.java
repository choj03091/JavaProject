package com.cjt.board.dtos;

import java.util.Date;
import org.apache.ibatis.type.Alias;

import lombok.Data;

//@Data
@Alias(value = "dogDto") //mapper.xml에서 사용하는 변수명 설정
public class DogDto {
	
    private int dogId;
    private int memberId;
    private String dogName;
    private String dogBreed;
    private int dogAge;
    private double dogWeight;
    private String dogGender;
    private String dogDrug;
    private String dogBlood;
    private Date regDate;
    private String delFlag;

    // 회원 정보 추가
    private String memberUsername; // 회원 ID
    private String memberName;     // 회원 이름

    // Getters and Setters
    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public int getDogAge() {
        return dogAge;
    }

    public void setDogAge(int dogAge) {
        this.dogAge = dogAge;
    }

    public double getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(double dogWeight) {
        this.dogWeight = dogWeight;
    }

    public String getDogGender() {
        return dogGender;
    }

    public void setDogGender(String dogGender) {
        this.dogGender = dogGender;
    }

    public String getDogDrug() {
        return dogDrug;
    }

    public void setDogDrug(String dogDrug) {
        this.dogDrug = dogDrug;
    }

    public String getDogBlood() {
        return dogBlood;
    }

    public void setDogBlood(String dogBlood) {
        this.dogBlood = dogBlood;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getMemberUsername() {
        return memberUsername;
    }

    public void setMemberUsername(String memberUsername) {
        this.memberUsername = memberUsername;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

	@Override
	public String toString() {
		return "DogDto [dogId=" + dogId + ", memberId=" + memberId + ", dogName=" + dogName + ", dogBreed=" + dogBreed
				+ ", dogAge=" + dogAge + ", dogWeight=" + dogWeight + ", dogGender=" + dogGender + ", dogDrug="
				+ dogDrug + ", dogBlood=" + dogBlood + ", regDate=" + regDate + ", delFlag=" + delFlag
				+ ", memberUsername=" + memberUsername + ", memberName=" + memberName + "]";
	}
    
}
