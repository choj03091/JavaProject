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
import org.springframework.web.multipart.MultipartRequest;

import com.cjt.board.command.InsertDogCommand;
import com.cjt.board.command.UpdateBoardCommand;
import com.cjt.board.command.UpdateDogCommand;
import com.cjt.board.dtos.DogDto;
import com.cjt.board.service.DogService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    @GetMapping("/dogList")
    public String getDogList(HttpSession session, Model model) {
        String role = (String) session.getAttribute("role");
        int memberId = (int) session.getAttribute("memberId");

        List<DogDto> dogList;
        if ("ADMIN".equals(role)) {
            dogList = dogService.getAllDogs(); // ADMIN 역할인 경우 전체 목록 조회
        } else {
            dogList = dogService.getDogsByMemberId(memberId); // USER 역할인 경우 해당 회원의 목록 조회
        }

        model.addAttribute("dogList", dogList);
        return "dog/dogList";
    }
    
    @GetMapping(value = "/dogInsert")
    public String dogInsertForm(Model model, HttpSession session) {
        Integer memberId = (Integer) session.getAttribute("memberId");
        System.out.println("세션에서 가져온 memberId: " + memberId);  // 디버깅용 출력

        if (memberId == null) {
            // 세션에 memberId가 없을 경우 로그인 페이지로 리다이렉트
            return "redirect:/user/login";
        }
        
        // 세션에서 가져온 memberId를 InsertDogCommand에 설정
        InsertDogCommand insertDogCommand = new InsertDogCommand();
        insertDogCommand.setMemberId(memberId);
        System.out.println("InsertDogCommand에 설정된 memberId: " + insertDogCommand.getMemberId());  // 디버깅용 출력
        
        model.addAttribute("insertDogCommand", insertDogCommand);
        return "dog/dogInsertForm";
    }
    
    @PostMapping(value = "/dogInsert")
    public String dogInsert(@Validated InsertDogCommand insertDogCommand,
                            BindingResult result,
                            HttpServletRequest request,
                            Model model) throws IllegalStateException, IOException {
        if (result.hasErrors()) {
            System.out.println("반려견 정보를 모두 입력하세요");
            return "dog/dogInsertForm";
        }
        
        System.out.println("폼으로부터 받은 memberId: " + insertDogCommand.getMemberId());  // 디버깅용 출력
        
        if (insertDogCommand.getMemberId() == null || insertDogCommand.getMemberId() == 0) {
            System.out.println("회원 ID가 설정되지 않았습니다.");
            return "redirect:/user/login";
        }

        dogService.insertDog(insertDogCommand);
        
        return "redirect:/dog/dogList";
    }
    
    //디테일
    @GetMapping(value = "/dogDetail")
    public String dogDetail(int dogId, Model model) {
    	DogDto dto = dogService.getDog(dogId);
    	
    	model.addAttribute("updateDogCommand", new UpdateDogCommand());
    	
    	model.addAttribute("dto", dto);
    	System.out.println(dto);
    	return "dog/dogDetail";
    }
    
    @PostMapping(value = "/dogUpdate")
    public String dogUpdate(
            @Validated UpdateDogCommand updateDogCommand,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            System.out.println("수정내용을 모두 입력하세요");
            DogDto dto = dogService.getDog(updateDogCommand.getDogId());
            model.addAttribute("dto", dto);
            return "dog/dogDetail";
        }

        dogService.updateDog(updateDogCommand);
        
        // 수정을 완료한 후 dogList로 리다이렉트
        return "redirect:/dog/dogList";
    }
    
    @PostMapping("/mulDel")
    public String mulDel(@RequestParam("dogIds")List<Integer> dogIds, Model model) {
    	if(dogIds == null || dogIds.isEmpty()) {
    		model.addAttribute("errorMessage", "삭제할 반려견을 선택하지 않았습니다.");
    		return "dog/dogList";
    	}
    	
    	dogService.mulDel(dogIds);
    	
    	System.out.println("삭제된 반려견 ID 목록:" + dogIds);
    	
    	return "redirect:/dog/dogList";
    }
}
