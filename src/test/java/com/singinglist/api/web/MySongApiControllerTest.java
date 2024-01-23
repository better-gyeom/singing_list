package com.singinglist.api.web;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.domain.posts.MySongRepository;
import com.singinglist.api.service.posts.MySongService;
import com.singinglist.api.web.dto.MySongSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MySongApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MySongRepository mySongRepository;

    @Autowired
    private MySongService mySongService;

    @AfterEach
    public void tearDown() throws Exception {
        mySongRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";

        MySongSaveRequestDto requestDto = MySongSaveRequestDto.builder().
                title(title).genre(genre).author(author).build();


        String url = "http://localhost:" + port + "/api/mysong-list";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        MySong mySong = mySongRepository.findTitleAndAuthor(title, author).orElseThrow();
        assertThat(mySong.getTitle()).isEqualTo(title);
        assertThat(mySong.getGenre()).isEqualTo(genre);
        assertThat(mySong.getAuthor()).isEqualTo(author);


    }

//    @Test
//    public void Posts_수정된다() throws Exception {
//        //given
//        MySong savedMySong = mySongRepository.save(MySong.builder()
//                .title("title2")
//                .genre("genre")
//                .author("author")
//                .build());
//
//        Long updateId = savedMySong.getId();
//        String expectedTitle = "title2";
//        String expectedContent = "content2";
//
//        MySongUpdateRequestDto requestDto = MySongUpdateRequestDto.builder()
//                .title(expectedTitle)
//                .content(expectedContent)
//                .build();
//
//        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
//        HttpEntity<MySongUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//        List<MySong> all = mySongRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getGenre()).isEqualTo(expectedContent);
//    }


}