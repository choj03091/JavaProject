package com.cjt.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cjt.board.dtos.FileBoardDto;
import com.cjt.board.mapper.FileMapper;

@Service
public class FileService {

    @Autowired
    private FileMapper fileMapper;

    // 파일 업로드 및 정보 저장
    public List<FileBoardDto> uploadFiles(String uploadPath, List<MultipartFile> files, int boardId) throws IOException {
        List<FileBoardDto> fileBoardDtoList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                // 파일 저장 로직
                String originalFilename = file.getOriginalFilename();
                String storedFilename = saveFile(uploadPath, file);

                // FileBoardDto 객체 생성
                FileBoardDto fileBoardDto = new FileBoardDto();
                fileBoardDto.setBoardId(boardId);
                fileBoardDto.setOrigin_filename(originalFilename);
                fileBoardDto.setStored_filename(storedFilename);

                // 데이터베이스에 파일 정보 저장
                fileMapper.insertFileBoard(fileBoardDto);
                fileBoardDtoList.add(fileBoardDto);
            }
        }
        return fileBoardDtoList;
    }

    // 파일 저장 메서드
    private String saveFile(String uploadPath, MultipartFile file) throws IOException {
        // 파일 저장 경로 생성
        File targetFile = new File(uploadPath, file.getOriginalFilename());
        file.transferTo(targetFile); // 파일 저장
        return targetFile.getName(); // 저장된 파일 이름 반환
    }
    
    // 특정 게시물의 파일 정보 조회
    public List<FileBoardDto> getFilesByBoardId(int boardId) {
        return fileMapper.getFilesByBoardId(boardId);
    }
    
    // 특정 게시물의 파일 삭제
    public void deleteFilesByBoardId(int boardId) {
        fileMapper.deleteFilesByBoardId(boardId);
    }
}
