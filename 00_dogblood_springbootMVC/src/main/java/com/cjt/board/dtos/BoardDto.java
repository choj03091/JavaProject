package com.cjt.board.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.cjt.board.dtos.FileBoardDto;

@Alias(value = "boardDto") //mapper.xml에서 사용하는 변수명 설정
public class BoardDto {
    private int boardId;     // 게시글 순번
    private int memberId;      // 작성자 ID (회원 ID)
    private String title;          // 제목
    private String content;        // 내용
    private Date regDate;          // 작성일
    private String delflag;        // 삭제 여부 (Y/N)
    
    //회원 정보 추가
    private String memberUsername;
    private String memberName;
    
	//Join용 맴버필드
	private List<FileBoardDto> fileBoardDto;

	public BoardDto() {
		super();
	}
	
	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", memberId=" + memberId + ", title=" + title + ", content=" + content
				+ ", regDate=" + regDate + ", delflag=" + delflag + ", memberUsername=" + memberUsername
				+ ", memberName=" + memberName + ", fileBoardDto=" + fileBoardDto + "]";
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getDelflag() {
		return delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
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

	public List<FileBoardDto> getFileBoardDto() {
		return fileBoardDto;
	}

	public void setFileBoardDto(List<FileBoardDto> fileBoardDto) {
		this.fileBoardDto = fileBoardDto;
	}

	public BoardDto(int boardId, int memberId, String title, String content, Date regDate, String delflag,
			String memberUsername, String memberName, List<FileBoardDto> fileBoardDto) {
		super();
		this.boardId = boardId;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.delflag = delflag;
		this.memberUsername = memberUsername;
		this.memberName = memberName;
		this.fileBoardDto = fileBoardDto;
	}



}
