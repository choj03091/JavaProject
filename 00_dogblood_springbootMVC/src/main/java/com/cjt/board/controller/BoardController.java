package com.cjt.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cjt.board.command.DelBoardCommand;
import com.cjt.board.command.DelOneBoardCommand;
import com.cjt.board.command.InsertBoardCommand;
import com.cjt.board.command.UpdateBoardCommand;
import com.cjt.board.dtos.BoardDto;
import com.cjt.board.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시물 목록 조회
    @GetMapping("/boardList")
    public String getBoardList(Model model) {
        List<BoardDto> list = boardService.getAllList();
        model.addAttribute("list", list);
        return "board/boardList"; // boardList.html로 이동
    }

    // 게시물 추가 폼
    @GetMapping("/boardInsert")
    public String boardInsertForm(Model model) {
        model.addAttribute("insertBoardCommand", new InsertBoardCommand());
        return "board/boardInsertForm"; // boardInsertForm.html로 이동
    }

    @PostMapping("/boardInsert")
    public String boardInsert(@Validated InsertBoardCommand insertBoardCommand,
                              BindingResult result,
                              @RequestParam("files") List<MultipartFile> files,
                              HttpServletRequest request,
                              HttpSession session) throws IOException {
        // 세션에서 memberId 가져오기
        Integer memberId = (Integer) session.getAttribute("memberId");
        insertBoardCommand.setMemberId(memberId); // memberId 설정

        if (result.hasErrors()) {
            return "board/boardInsertForm"; // 에러가 있는 경우 폼으로 돌아감
        }

        boardService.insertBoard(insertBoardCommand, files, request); // 게시물 추가
        return "redirect:/board/boardList"; // 게시물 목록으로 리다이렉트
    }

    @GetMapping("/boardDetail")
    public String boardDetail(@RequestParam("boardId") int boardId, Model model) {
        BoardDto dto = boardService.getBoard(boardId);
        model.addAttribute("dto", dto);

        // UpdateBoardCommand 객체 추가
        UpdateBoardCommand updateBoardCommand = new UpdateBoardCommand();
        updateBoardCommand.setBoardId(dto.getBoardId());
        updateBoardCommand.setTitle(dto.getTitle());
        updateBoardCommand.setContent(dto.getContent());
        model.addAttribute("updateBoardCommand", updateBoardCommand);

        return "board/boardDetail";
    }

    // 게시물 수정 처리
    @PostMapping("/boardUpdate")
    public String boardUpdate(@Validated UpdateBoardCommand updateBoardCommand,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "board/boardDetail"; // 에러가 있는 경우 상세보기로 돌아감
        }

        boardService.updateBoard(updateBoardCommand); // 게시물 수정
        return "redirect:/board/boardList"; // 게시물 목록으로 리다이렉트
    }

    // 여러 게시물 삭제
    @PostMapping("/mulDel")
    public String mulDel(@RequestParam("boardIds") List<Integer> boardIds, Model model) {
        if (boardIds == null || boardIds.isEmpty()) {
            model.addAttribute("errorMessage", "삭제할 게시물이 선택되지 않았습니다.");
            return "board/boardList"; // 삭제할 게시물이 없을 경우 다시 목록으로
        }

        // 게시물 삭제
        try {
            boardService.mulDel(boardIds);
            System.out.println("삭제된 boardID 목록:" + boardIds);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시물 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return "board/boardList"; // 오류 발생 시 다시 목록으로
        }

        return "redirect:/board/boardList"; // 게시물 목록으로 리다이렉트
    }
    
    @PostMapping("/boardDelete")
    public String boardDelete(@Validated DelOneBoardCommand delOneBoardCommand, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "board/boardDetail"; // 에러가 있는 경우 상세보기로 돌아감
        }

        try {
            int boardId = delOneBoardCommand.getBoardId();
            boardService.deleteBoard(boardId); // Service를 통해 삭제
            return "redirect:/board/boardList"; // 삭제 후 게시판 목록으로 리다이렉트
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시물 삭제 중 오류가 발생했습니다: " + e.getMessage());
            return "board/boardDetail"; // 오류 발생 시 상세보기로 돌아감
        }
    }



}
