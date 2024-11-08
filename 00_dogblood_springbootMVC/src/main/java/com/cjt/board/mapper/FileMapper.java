package com.cjt.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cjt.board.dtos.FileBoardDto;

@Mapper
public interface FileMapper {

	// 파일 정보 추가
	public void insertFileBoard(FileBoardDto fileBoardDto);

	// 특정 게시물의 파일 정보 조회
	List<FileBoardDto> getFilesByBoardId(int boardId);

	// 특정 게시물의 파일 정보 삭제
	public void deleteFilesByBoardId(int boardId);

	public void deleteByBoardId(int boardId); // 게시물 ID로 파일 삭제
}





