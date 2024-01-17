package com.singinglist.api.service.posts;

import com.singinglist.api.domain.posts.Posts;
import com.singinglist.api.domain.posts.TestRepository;
import com.singinglist.api.web.dto.PostsResponseDto;
import com.singinglist.api.web.dto.PostsSaveRequestDto;
import com.singinglist.api.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostsService {
    @Autowired
    private final TestRepository testRepository;

    public Long save(PostsSaveRequestDto requestDto) {
        return testRepository.save(requestDto.toEntity()).getId();
    }

    public Long update(Long id, PostsUpdateRequestDto requestDto) { //JPA의 영속성 컨텍스트 때문에 쿼리를 날리는 부분이 없다(?)
        Posts posts = testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }
}
