package com.singinglist.api.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestRepositoryTest {

    @Autowired
    TestRepository testRepository;

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void post_아이디로_찾기() {
        //given
        String title = "면접을 위한 CS 전공지식 노트";
        String content = "3.2 메모리";
        String author = "주홍철";

        Posts savedPosts = postsRepository.save(Posts.builder() //save는 안만들었기 때문에 기존 JPA 사용 (나중에 없앨 예정)
                .title(title)
                .content(content)
                .author(author).build());
        Long postId = savedPosts.getId();
        //when
        Posts post = testRepository
                .findById(savedPosts.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

        //then
        assertThat(post.getTitle()).isEqualTo(title);
    }
}
