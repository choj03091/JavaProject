package com.cjt.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.cjt.board.dtos.BoardDto;

@Mapper
public interface BoardMapper {

	//글목록
	public List<BoardDto> getAllList();
	//글상세조회
	public BoardDto getBoard(int boardId);
	//글추가
	public boolean insertBoard(BoardDto dto);
	//글 수정
	public boolean updateBoard(BoardDto dto);
	
	public void deleteByMemberId(int memberId);
	
    // 특정 게시물 삭제
    public void deleteByBoardId(int boardId); // 단일 게시물 삭제 메서드

    // 여러 게시물 삭제
    public void mulDel(List<Integer> boardIds); // 여러 게시물 삭제 메서드
}







