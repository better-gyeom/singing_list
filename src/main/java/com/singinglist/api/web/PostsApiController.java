package com.singinglist.api.web;

import com.singinglist.api.service.posts.PostsService;
import com.singinglist.api.web.dto.PostsResponseDto;
import com.singinglist.api.web.dto.PostsSaveRequestDto;
import com.singinglist.api.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts") //등록기능
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}") //수정기능
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}") //조회기능
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
