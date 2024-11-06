package com.cjt.board.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.cjt.board.command.DelBoardCommand;
import com.cjt.board.command.InsertBoardCommand;
import com.cjt.board.command.UpdateBoardCommand;
import com.cjt.board.dtos.BoardDto;
import com.cjt.board.dtos.FileBoardDto;
import com.cjt.board.service.BoardService;
import com.cjt.board.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private FileService fileService;
	
	@GetMapping(value = "/boardList")
	public String boardList(Model model) {
		System.out.println("글목록 보기");
		
		List<BoardDto> list=boardService.getAllList();
		model.addAttribute("list", list);
		model.addAttribute("delBoardCommand", new DelBoardCommand());
		return "board/boardList";// forward 기능, "redirect:board/boardList"
	}
	
	@GetMapping(value = "/boardInsert")
	public String boardInsertForm(Model model) {
		model.addAttribute("insertBoardCommand", new InsertBoardCommand());
		return "board/boardInsertForm";
	}
	
	@PostMapping(value = "/boardInsert")
	public String boardInsert(@Validated InsertBoardCommand insertBoardCommand 
			                ,BindingResult result
			                ,MultipartRequest multipartRequest //multipart data를 처리할때 사용
							,HttpServletRequest request
			                ,Model model) throws IllegalStateException, IOException {
		if(result.hasErrors()) {
			System.out.println("글을 모두 입력하세요");
			return "board/boardInsertForm";
		}
		
		boardService.insertBoard(insertBoardCommand,multipartRequest
				                ,request);
		
		return "redirect:/board/boardList";
	}
	
	//상세보기
	@GetMapping(value = "/boardDetail")
	public String boardDetail(@RequestParam int board_seq, HttpServletRequest request, Model model) {
	    BoardDto dto = boardService.getBoard(board_seq);
	    model.addAttribute("updateBoardCommand", new UpdateBoardCommand());
	    model.addAttribute("dto", dto);

	    // 현재 세션에서 memberId 가져오기
	    HttpSession session = request.getSession();
	    Object id = session.getAttribute("id");
	    
	    // 콘솔에 출력
	    System.out.println("Session memberId: " + id);
	    System.out.println("Board author ID: " + dto.getId());

	    return "board/boardDetail";
	}
	
	//수정하기
	@PostMapping(value = "/boardUpdate")
	public String boardUpdate(
				@Validated UpdateBoardCommand updateBoardCommand
				,BindingResult result
				,Model model) {
		
		if(result.hasErrors()) {
			System.out.println("수정내용을 모두 입력하세요");
			//코드 추가--------------------------------------------
			BoardDto dto=boardService.getBoard(updateBoardCommand.getBoard_seq());
			model.addAttribute("dto", dto);
			//--------------------------------------------------
			return "board/boardDetail";
		}
		
		boardService.updateBoard(updateBoardCommand);
		
		return "redirect:/board/boardList";
	}
	
	@GetMapping(value = "/download")
	public void download(int file_seq, HttpServletRequest request
			                         , HttpServletResponse response) throws UnsupportedEncodingException {
		
		FileBoardDto fdto=fileService.getFileInfo(file_seq);//파일정보가져오기
		
		fileService.fileDownload(fdto.getOrigin_filename()
				                ,fdto.getStored_filename()
				                ,request,response);
	}
	
	@RequestMapping(value="mulDel",method = {RequestMethod.POST,RequestMethod.GET})
	public String mulDel(@Validated DelBoardCommand delBoardCommand
						 ,BindingResult result
			             , Model model) {
		if(result.hasErrors()) {
			System.out.println("최소하나 체크하기");
			List<BoardDto> list=boardService.getAllList();
			model.addAttribute("list", list);
			return "board/boardlist";
		}
		boardService.mulDel(delBoardCommand.getSeq());
		System.out.println("글삭제함");
		return "redirect:/board/boardList";
	}
	
}














