package com.cjt.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.cjt.board.command.AddUserCommand;
import com.cjt.board.command.DelUserCommand;
import com.cjt.board.command.InsertBoardCommand;
import com.cjt.board.command.InsertUserCommand;
import com.cjt.board.command.LoginCommand;
import com.cjt.board.command.UpdateDogCommand;
import com.cjt.board.command.UpdateUserCommand;
import com.cjt.board.dtos.MemberDto;
import com.cjt.board.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class MemberController {

    @Autowired
    private MemberService memberService;
    
    @GetMapping(value = "/addUser")
    public String addUserForm(Model model) {
        System.out.println("회원가입 폼 이동");
        
        model.addAttribute("addUserCommand", new AddUserCommand());
        
        return "member/addUserForm";
    }
    
    @PostMapping(value = "/addUser")
    public String addUser(@Validated AddUserCommand addUserCommand,
                          BindingResult result, Model model,
                          HttpServletRequest request) {  // HttpServletRequest 추가
        System.out.println("회원가입 시도");
        
        if(result.hasErrors()) {
            System.out.println("회원가입 유효값 오류");
            return "member/addUserForm";
        }
        
        try {
            // 회원가입 수행
            memberService.addUser(addUserCommand);
            System.out.println("회원가입 성공");

            // 회원가입 후 자동 로그인 처리
            LoginCommand loginCommand = new LoginCommand();
            loginCommand.setId(addUserCommand.getId());
            loginCommand.setPassword(addUserCommand.getPassword());  // 사용자가 입력한 비밀번호

            // 로그인 서비스 호출
            String loginPath = memberService.login(loginCommand, request, model);
            
            // 로그인 성공 여부 확인
            if (loginPath.startsWith("redirect:/")) {
                return "redirect:/";  // 로그인 후 홈 페이지로 리다이렉트
            }
        } catch (Exception e) {
            System.out.println("회원가입 실패");
            e.printStackTrace();
            return "redirect:addUser";  // 회원가입 페이지로 리다이렉트
        }

        return "redirect:/";  // 기본적으로 홈 페이지로 리다이렉트
    }
    
    @ResponseBody
    @GetMapping(value = "/idChk")
    public Map<String, String> idChk(String id){
        System.out.println("ID 중복 체크 시도");
        
        String resultId = memberService.idChk(id);
        Map<String, String> map = new HashMap<>();
        map.put("id", resultId);
        return map;
    }
    
    @GetMapping(value = "/login")
    public String loginForm(Model model) {
        model.addAttribute("loginCommand", new LoginCommand());
        return "member/login";
    }
    
	@PostMapping(value = "/login")
	public String login(@Validated LoginCommand loginCommand
			           ,BindingResult result
			           ,Model model
			           ,HttpServletRequest request) {
		if(result.hasErrors()) {
			System.out.println("로그인 유효값 오류");
			return "member/login";
		}
		
		String path=memberService.login(loginCommand, request, model);
		
		return path;
	}

    
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) {
        System.out.println("로그아웃");
        request.getSession().invalidate();
        return "redirect:/";
    }
    
    @GetMapping(value = "/userList")
    public String userList(Model model) {
    	System.out.println("유저리스트 보기");
    	
    	List<MemberDto> list = memberService.getAllUserList();
    	model.addAttribute("list", list);
    	return "user/userList";
    }
    
    @GetMapping(value = "/userDetail")
    public String userDetail(int memberId, Model model) {
    	MemberDto dto = memberService.getUser(memberId);
    	
    	model.addAttribute("updateUserCommand", new UpdateUserCommand());
    	
    	model.addAttribute("dto", dto);
    	System.out.println(dto);
    	return "user/userDetail";
    }
    
    @PostMapping(value = "/userUpdate")
    public String userUpdate(
    		@Validated UpdateUserCommand updateUserCommand,
            BindingResult result,
            Model model) {
    	if(result.hasErrors()) {
    		System.out.println("수정내용을 모두 입력하세요.");
    		MemberDto dto = memberService.getUser(updateUserCommand.getMemberId());
    		model.addAttribute("dto", dto);
    		return "user/userDetail";
    	}
    	
    	memberService.updateUser(updateUserCommand);
    	
    	return "redirect:/user/userList";
    }
    
    @GetMapping(value = "/userInsert")
    public String userInsertForm(Model model) {
    	model.addAttribute("insertUserCommand", new InsertUserCommand());
    	return "user/userInsertForm";
    }
    
	@PostMapping(value = "/userInsert")
	public String boardInsert(@Validated InsertUserCommand insertUserCommand 
			                ,BindingResult result
							,HttpServletRequest request
			                ,Model model) throws IllegalStateException, IOException {
		if(result.hasErrors()) {
			System.out.println("글을 모두 입력하세요");
			return "user/userInsertForm";
		}
		
		memberService.insertUser(insertUserCommand);
		
		return "redirect:/user/userList";
	}
}

