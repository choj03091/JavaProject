package com.cjt.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.cjt.board.dtos.MemberDto;

@Mapper
public interface MemberMapper {
	
    public boolean addUser(MemberDto dto);
	
    public String idChk(String id);
	
    public MemberDto loginUser(String id);

    // ID를 통해 사용자의 정보를 조회하는 메서드 추가
    public MemberDto findMemberById(String id);

    public List<MemberDto> getAllUserList();
    public MemberDto getUser(int memberId);
    public boolean updateUser(MemberDto dto);
    public boolean insertUser(MemberDto dto);
    void deleteByMemberId(int memberId); // 특정 사용자의 삭제 메소드
    void mulDel(List<Integer> memberIds); // 다수의 사용자 삭제 메소드
    public boolean updateUserInfo(MemberDto userdto);
}