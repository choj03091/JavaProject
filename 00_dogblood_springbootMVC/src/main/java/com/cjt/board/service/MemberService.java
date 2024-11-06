package com.cjt.board.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.cjt.board.command.AddUserCommand;
import com.cjt.board.command.InsertUserCommand;
import com.cjt.board.command.LoginCommand;
import com.cjt.board.command.UpdateUserCommand;
import com.cjt.board.dtos.MemberDto;
import com.cjt.board.mapper.MemberMapper;
import com.cjt.board.status.RoleStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
public class MemberService {

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean addUser(AddUserCommand addUserCommand) {
        MemberDto mdto = new MemberDto();
        mdto.setId(addUserCommand.getId());
        mdto.setName(addUserCommand.getName());

        // Password 암호화하여 저장
        mdto.setPassword(passwordEncoder.encode(addUserCommand.getPassword()));

        mdto.setEmail(addUserCommand.getEmail());
        mdto.setAddress(addUserCommand.getAddress());
        mdto.setRole(RoleStatus.USER.toString()); // 등급 추가
        return memberMapper.addUser(mdto);
    }

    public String idChk(String id) {
        return memberMapper.idChk(id);
    }

    public MemberDto findMemberById(String id) {
        return memberMapper.findMemberById(id);
    }

    public String login(LoginCommand loginCommand, HttpServletRequest request, Model model) {
        System.out.println("로그인 시도: ID = " + loginCommand.getId());

        MemberDto dto = memberMapper.loginUser(loginCommand.getId());
        String path = "redirect:/"; // 기본 리다이렉트 경로 (일반 사용자)

        if (dto != null) {
            if (passwordEncoder.matches(loginCommand.getPassword(), dto.getPassword())) {
                System.out.println("로그인 성공: 패스워드 일치");
                request.getSession().setAttribute("mdto", dto);
                request.getSession().setAttribute("role", dto.getRole());
                request.getSession().setAttribute("memberId", dto.getMemberId());

                // Admin 역할인 경우 관리자 페이지로 리다이렉트 경로 설정
                if ("ADMIN".equals(dto.getRole())) {
                    path = "redirect:/";
                    System.out.println("Admin 로그인 성공 - 관리자 기능 부여");
                } else {
                    System.out.println("일반 사용자 로그인 성공 - 메인 페이지로 리다이렉트");
                }
            } else {
                System.out.println("로그인 실패: 패스워드 불일치");
                model.addAttribute("msg", "패스워드를 확인하세요");
                path = "member/login";
            }
        } else {
            System.out.println("로그인 실패: 사용자 정보 없음");
            model.addAttribute("msg", "아이디를 확인하세요");
            path = "member/login";
        }

        return path;
    }

    public List<MemberDto> getAllUserList(){
    	return memberMapper.getAllUserList();
    }
    
    public MemberDto getUser(int memberId) {
    	return memberMapper.getUser(memberId);
    }
    
    public boolean updateUser(UpdateUserCommand updateUserCommand) {
        MemberDto dto = new MemberDto();
        dto.setMemberId(updateUserCommand.getMemberId());
        dto.setAddress(updateUserCommand.getAddress());
        dto.setEmail(updateUserCommand.getEmail());
        dto.setRole(updateUserCommand.getRole());
        dto.setDelFlag(updateUserCommand.getDelFlag());
        return memberMapper.updateUser(dto);
    }
    
    public void insertUser(InsertUserCommand insertUserCommand) {
        MemberDto dto = new MemberDto();
        dto.setId(insertUserCommand.getId());
        dto.setPassword(passwordEncoder.encode(insertUserCommand.getPassword()));
        dto.setName(insertUserCommand.getName());
        dto.setAddress(insertUserCommand.getAddress());
        dto.setEmail(insertUserCommand.getEmail());
        dto.setRole(insertUserCommand.getRole());
        dto.setDelFlag(insertUserCommand.getDelFlag());
        memberMapper.insertUser(dto);
    	
    }
}

