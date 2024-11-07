package com.cjt.board.service;

import com.cjt.board.command.InsertDogCommand;
import com.cjt.board.command.UpdateDogCommand;
import com.cjt.board.dtos.DogDto;
import com.cjt.board.mapper.DogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {

    @Autowired
    private DogMapper dogMapper;

    public List<DogDto> getAllDogs() {
        return dogMapper.getAllDogs(); // 모든 반려견 목록 조회
    }

    public List<DogDto> getDogsByMemberId(int memberId) {
        return dogMapper.getDogsByMemberId(memberId); // 특정 회원의 반려견 목록 조회
    }
    // 반려견 정보 추가
    public void insertDog(InsertDogCommand insertDogCommand) {
        DogDto dogDto = new DogDto();
        dogDto.setMemberId(insertDogCommand.getMemberId());
        dogDto.setDogName(insertDogCommand.getDogName());
        dogDto.setDogBreed(insertDogCommand.getDogBreed());
        dogDto.setDogAge(insertDogCommand.getDogAge());
        dogDto.setDogWeight(insertDogCommand.getDogWeight());
        dogDto.setDogGender(insertDogCommand.getDogGender());
        dogDto.setDogDrug(insertDogCommand.getDogDrug());
        dogDto.setDogBlood(insertDogCommand.getDogBlood());
        
        dogMapper.insertDog(dogDto);
    }
    
    //디테일조회
    public DogDto getDog(int dogId) {
    	return dogMapper.getDog(dogId);
    }
    
 // 수정
    public boolean updateDog(UpdateDogCommand updateDogCommand) {
        DogDto dto = new DogDto();
        dto.setDogId(updateDogCommand.getDogId());
        dto.setDogName(updateDogCommand.getDogName());
        dto.setDogBreed(updateDogCommand.getDogBreed());
        dto.setDogAge(updateDogCommand.getDogAge());
        dto.setDogWeight(updateDogCommand.getDogWeight());
        dto.setDogGender(updateDogCommand.getDogGender());
        dto.setDogDrug(updateDogCommand.getDogDrug());
        dto.setDogBlood(updateDogCommand.getDogBlood());

        return dogMapper.updateDog(dto);
    }
    
    public void mulDel(List<Integer> dogIds) {
    	for(Integer dogId : dogIds) {
    		dogMapper.deleteByDogId(dogId);
    	}
    }
   

}
