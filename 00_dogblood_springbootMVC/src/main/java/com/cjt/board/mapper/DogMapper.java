package com.cjt.board.mapper;

import com.cjt.board.dtos.DogDto;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DogMapper {
    public List<DogDto> getAllDogs(); // 전체 반려견 목록 조회
    public List<DogDto> getDogsByMemberId(int memberId); // 특정 회원의 반려견 목록 조회
    public boolean insertDog(DogDto dto);
    public DogDto getDog(int dogId);
    public boolean updateDog(DogDto dto);
    public void deleteByMemberId(int memberId);
    public void deleteByDogId(int dogId);
    public void mulDel(List<Integer> dogIds);
}
