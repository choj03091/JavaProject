package com.cjt.board.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    // 게시물 목록 조회
    public List<BoardDto> getAllList() {
        return boardMapper.getAllList();
    }

    // 게시물 추가
    @Transactional(rollbackFor = Exception.class)
    public void insertBoard(InsertBoardCommand insertBoardCommand, List<MultipartFile> files, HttpServletRequest request) throws IOException {
        BoardDto boardDto = new BoardDto();
        boardDto.setMemberId(insertBoardCommand.getMemberId());
        boardDto.setTitle(insertBoardCommand.getTitle());
        boardDto.setContent(insertBoardCommand.getContent());

        // 새 게시물 추가
        boardMapper.insertBoard(boardDto);

        // 파일 업로드 및 정보 저장
        String uploadPath = request.getSession().getServletContext().getRealPath("/upload");
        fileService.uploadFiles(uploadPath, files, boardDto.getBoardId());
    }

    // 게시물 상세 조회
    public BoardDto getBoard(int boardId) {
        return boardMapper.getBoard(boardId);
    }

    // 게시물 수정
    public boolean updateBoard(UpdateBoardCommand updateBoardCommand) {
        BoardDto dto = new BoardDto();
        dto.setBoardId(updateBoardCommand.getBoardId());
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
    
    @Transactional
    public void deleteBoard(int boardId) {
        // 게시물에 관련된 파일도 삭제하는 로직 추가 필요
        fileMapper.deleteByBoardId(boardId); // 파일 삭제 (FileMapper에서 추가 필요)
        boardMapper.deleteByBoardId(boardId); // 게시물 삭제
    }

}
