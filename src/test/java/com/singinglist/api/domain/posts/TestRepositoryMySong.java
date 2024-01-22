package com.singinglist.api.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestRepositoryMySong {

    @Autowired
    MySongRepository mySongRepository;

    @AfterEach
    public void tearDown() throws Exception {
        mySongRepository.deleteAll();
    }


    @Test
    public void post_아이디로_찾기() {
        //given
        String title = "면접을 위한 CS 전공지식 노트";
        String content = "3.2 메모리";
        String author = "주홍철";

        MySong savedMySong = mySongRepository.save(MySong.builder() //save는 안만들었기 때문에 기존 JPA 사용 (나중에 없앨 예정)
                .title(title)
                .content(content)
                .author(author).build());
        Long postId = savedMySong.getId();
        //when
        MySong post = mySongRepository
                .findById(savedMySong.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + postId));

        //then
        assertThat(post.getTitle()).isEqualTo(title);
    }

    @Test
    public void save_test() {
        //given
        String title = "짜증나";
        String content = "명선이는 나빠";
        String author = "유다윤";

        MySong saveMySong = mySongRepository.save(MySong.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());

        //when
        assertThat(saveMySong.getTitle()).isEqualTo(title);

        //then
    }
}
