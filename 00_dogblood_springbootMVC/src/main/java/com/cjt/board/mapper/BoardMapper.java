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
	//글 삭제
//    void mulDel(Integer memberId); // 단일 memberId를 받는 메소드 추가

	
}







