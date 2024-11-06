package com.cjt.board.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cjt.board.dtos.MemberDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	    HttpSession session = request.getSession();
	    MemberDto mdto = (MemberDto) session.getAttribute("mdto");

	    // 로그인 페이지와 관련된 요청은 인터셉터가 무시하도록 설정
	    String uri = request.getRequestURI();
	    if (uri.equals("/user/login") || uri.equals("/user/addUser")) {
	        return true;
	    }

	    if (mdto == null) { // 로그인되지 않은 경우
	        System.out.println("로그인 필요");
	        response.sendRedirect("/user/login");
	        return false;
	    } else {
	        session.setAttribute("role", mdto.getRole());
	        session.setAttribute("memberId", mdto.getMemberId());
	        return true;
	    }
	}

}

