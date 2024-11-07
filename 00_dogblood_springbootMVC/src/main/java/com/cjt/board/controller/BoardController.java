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
import org.springframework.web.multipart.MultipartRequest;

import com.cjt.board.command.InsertBoardCommand;
import com.cjt.board.command.UpdateBoardCommand;
import com.cjt.board.command.UpdateDogCommand;
import com.cjt.board.dtos.BoardDto;
import com.cjt.board.dtos.DogDto;
import com.cjt.board.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/boardList")
    public String getBoardList(Model model) {
        List<BoardDto> list = boardService.getAllList();
        model.addAttribute("list", list);
        return "board/boardList";
    }
    
    @GetMapping(value = "/boardInsert")
    public String boardInsertForm(HttpSession session, Model model) {
        // 세션에서 memberId를 가져오기
        Integer memberId = (Integer) session.getAttribute("memberId");
        System.out.println("세션에서 가져온 memberId: " + memberId);  // 디버깅용 출력
        
        if (memberId == null) {
            // 세션에 memberId가 없을 경우 로그인 페이지로 리다이렉트
            return "redirect:/user/login";
        }
        
        // InsertBoardCommand 객체 생성 및 memberId 설정
        InsertBoardCommand insertBoardCommand = new InsertBoardCommand();
        insertBoardCommand.setMemberId(memberId);
        
        model.addAttribute("insertBoardCommand", insertBoardCommand);
        return "board/boardInsertForm"; // 게시물 추가 폼으로 이동
    }
    
    @PostMapping(value = "/boardInsert")
    public String boardInsert(@Validated InsertBoardCommand insertBoardCommand,
                              BindingResult result,
                              HttpServletRequest request) throws IllegalStateException, IOException {
        if (result.hasErrors()) {
            System.out.println("게시물 정보를 모두 입력하세요");
            return "board/boardInsertForm"; // 오류 시 게시물 추가 폼으로 돌아감
        }
        
        System.out.println("폼으로부터 받은 memberId: " + insertBoardCommand.getMemberId());  // 디버깅용 출력
        
        // 게시물 추가
        boardService.insertBoard(insertBoardCommand);
        
        return "redirect:/board/boardList"; // 게시물 목록으로 리다이렉트
    }
    
    @GetMapping(value = "/boardDetail")
    public String boardDetail(int boardId, Model model) {
        BoardDto dto = boardService.getBoard(boardId);
        
        model.addAttribute("updateBoardCommand", new UpdateBoardCommand());
        
        model.addAttribute("dto", dto);
        System.out.println(dto);
        return "board/boardDetail"; // 게시물 상세 페이지로 이동
    }
    
    @PostMapping(value = "/boardUpdate")
    public String dogUpdate(
            @Validated UpdateBoardCommand updateBoardCommand,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            System.out.println("수정내용을 모두 입력하세요");
            BoardDto dto = boardService.getBoard(updateBoardCommand.getBoardId());
            model.addAttribute("dto", dto);
            return "board/boardDetail";
        }

        boardService.updateBoard(updateBoardCommand);
        
        // 수정을 완료한 후 dogList로 리다이렉트
        return "redirect:/board/boardList";
    }

//    @PostMapping(value = "/mulDel")
//    public String mulDel(@Validated DelBoardCommand delBoardCommand,
//                         BindingResult result,
//                         Model model) {
//        if (result.hasErrors()) {
//            System.out.println("최소 하나 체크하기.");
//            List<BoardDto> list = boardService.getAllList();
//            model.addAttribute("list", list);
//            return "board/boardList"; // 오류 시 목록으로 돌아감
//        }
//        
//        boardService.mulDel(delBoardCommand.getSeq()); // 선택된 게시물 삭제
//        return "redirect:/board/boardList"; // 삭제 후 목록으로 리다이렉트
//    }
}
