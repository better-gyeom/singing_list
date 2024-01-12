package com.singinglist.api.domain.posts;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest //h2 데이터베이스를 자동으로 실행함
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach //test 실행되고 난 후
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        //데이블 posts에 insert/update 쿼리 실행
        postsRepository.save(Posts.builder().title(title).content(content).author("jojoldu@gmail.com").build());

        //when
        List<Posts> postsList = postsRepository.findAll(); //모든 데이터를 조회해오는 메소드

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

}
