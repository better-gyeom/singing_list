package com.singinglist.api.web;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.service.posts.MySongService;
import com.singinglist.api.web.dto.MySongInsertRequestDto;
import com.singinglist.api.web.dto.MySongResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MySongApiController {
    private final MySongService mySongService;

    //나의 노래리스트에 노래 등록하기
    @PostMapping("/api/mysong-list")
    public void insert(@RequestBody MySongInsertRequestDto requestDto) {
        mySongService.insert(requestDto);
    }

    //나의 노래리스트 조회
    @GetMapping("/api/mysong-list") //전체 조회기능
    public List<MySongResponseDto> findAll() {
        return mySongService.findAll();
    }


    @GetMapping("/api/mysong-list/{title}") //단건 조회기능
    public List<MySongResponseDto> findByTitle(@PathVariable String title) {
        return mySongService.findByTitle(title);
    }

    @DeleteMapping("/api/mysong-list/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        mySongService.deleteSongById(id);
    }

}
