package com.cjt.board.service;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartRequest;

import com.cjt.board.command.InsertBoardCommand;
import com.cjt.board.command.UpdateBoardCommand;
import com.cjt.board.dtos.BoardDto;
import com.cjt.board.dtos.FileBoardDto;
import com.cjt.board.mapper.BoardMapper;
import com.cjt.board.mapper.FileMapper;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private FileMapper fileMapper;
	@Autowired
	private FileService fileService;
	
	//글목록 조회
	public List<BoardDto> getAllList(){
		return boardMapper.getAllList();
	}

	public void insertBoard(InsertBoardCommand insertBoardCommand) {
		BoardDto boardDto = new BoardDto();
		boardDto.setMemberId(insertBoardCommand.getMemberId());
		boardDto.setTitle(insertBoardCommand.getTitle());
		boardDto.setContent(insertBoardCommand.getContent());
		boardMapper.insertBoard(boardDto);
	}
	
	//글 추가, 파일업로드및 파일정보 추가
//	@Transactional(rollbackFor = Exception.class)
//	public void insertBoard(InsertBoardCommand insertBoardCommand
//			              , MultipartRequest multipartRequest
//			              , HttpServletRequest request) 
//			              throws IllegalStateException, IOException {
//		//command:UI -> dto:DB 데이터 옮겨담기
//		BoardDto boardDto = new BoardDto();
//		boardDto.setMemberId(insertBoardCommand.getMemberId()); // memberId 추가
//		boardDto.setTitle(insertBoardCommand.getTitle());
//		boardDto.setContent(insertBoardCommand.getContent());
//		
//		//새글을 추가할때 파라미터로 전달된 boardDto객체에 자동으로,
//		//증가된 board_seq값이 저장
//		boardMapper.insertBoard(boardDto); //새글 추가
//
//		System.out.println("파일첨부여부:"
//		+ multipartRequest.getFiles("filename").get(0).isEmpty());
//
//		//첨부된 파일들이 있는 경우
//		if(!multipartRequest.getFiles("filename").get(0).isEmpty()) {
//			//파일 저장경로 설정: 절대경로, 상대경로
//			String filepath = request.getSession().getServletContext().getRealPath("upload");
//			System.out.println("파일저장경로:" + filepath);
//			//파일업로드 작업은 FileService쪽에서 업로드하고 업로드된 파일정보 반환
//			List<FileBoardDto> uploadFileList = fileService.uploadFiles(filepath, multipartRequest);
//			//파일정보를 DB에 추가
//			for (FileBoardDto fDto : uploadFileList) {
//				fileMapper.insertFileBoard(
//					new FileBoardDto(0, boardDto.getBoardId(), // 증가된 board_seq값을 넣는다 
//					                  fDto.getOrigin_filename(),
//					                  fDto.getStored_filename())
//				);
//			}
//		}
//	}
	
	//상세내용조회
	public BoardDto getBoard(int boardId) {
		return boardMapper.getBoard(boardId);
	}
	
	//수정하기
	public boolean updateBoard(UpdateBoardCommand updateBoardCommand) {
		BoardDto dto=new BoardDto();
		dto.setBoarId(updateBoardCommand.getBoardId());
		dto.setTitle(updateBoardCommand.getTitle());
		dto.setContent(updateBoardCommand.getContent());
		return boardMapper.updateBoard(dto);
	}

    // 여러 게시물 삭제
    public void mulDel(List<Integer> boardIds) {
        for (Integer boardId : boardIds) {
            boardMapper.deleteByBoardId(boardId); // 게시물 삭제
        }
    }

}






	