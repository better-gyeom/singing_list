package com.singinglist.api.web;

import com.singinglist.api.service.posts.MySongService;
import com.singinglist.api.web.dto.MySongResponseDto;
import com.singinglist.api.web.dto.MySongSaveRequestDto;
import com.singinglist.api.web.dto.MySongUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MySongApiController {
    private final MySongService mySongService;

    @PostMapping("/api/v1/posts") //등록기능
    public Long save(@RequestBody MySongSaveRequestDto requestDto) {
        return mySongService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}") //수정기능
    public Long update(@PathVariable Long id, @RequestBody MySongUpdateRequestDto requestDto) {
        return mySongService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}") //조회기능
    public MySongResponseDto findById(@PathVariable Long id) {
        return mySongService.findById(id);
    }
}
