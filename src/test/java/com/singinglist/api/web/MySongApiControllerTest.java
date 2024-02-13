package com.singinglist.api.web;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.domain.posts.MySongRepository;
import com.singinglist.api.service.posts.MySongService;
import com.singinglist.api.web.dto.MySongInsertRequestDto;
import com.singinglist.api.web.dto.MySongResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void 나의_노래리스트에_등록된다() throws Exception {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";
        LocalDateTime releaseDate = LocalDateTime.of(2023, Month.NOVEMBER, 10, 0, 0);

        MySongInsertRequestDto requestDto = MySongInsertRequestDto.builder().
                title(title).genre(genre).author(author).releaseDate(releaseDate).build();

        String url = "http://localhost:" + port + "/api/mysong-list";

        //when
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestDto, null);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<MySong> mySong = mySongRepository.findByTitle(title);
        MySong oneSong = mySong.get(0);
        assertThat(oneSong.getTitle()).isEqualTo(title);
        assertThat(oneSong.getGenre()).isEqualTo(genre);
        assertThat(oneSong.getAuthor()).isEqualTo(author);

    }

    @Test
    public void 나의_노래리스트_전체조회() throws Exception {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";
        LocalDateTime releaseDate = LocalDateTime.of(2023, Month.NOVEMBER, 10, 0, 0);

        MySong mySong = new MySong(title, genre, author, releaseDate);
        mySongRepository.insertSong(mySong);

        String url = "http://localhost:" + port + "/api/mysong-list";

        //when
        ResponseEntity<MySongResponseDto[]> responseEntity = restTemplate.getForEntity(url, MySongResponseDto[].class);
        System.out.println(responseEntity.getBody());
        List<MySongResponseDto> mySongList = Arrays.asList(responseEntity.getBody());

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        MySongResponseDto oneSong = mySongList.get(0);
        assertThat(oneSong.getTitle()).isEqualTo(title);
        assertThat(oneSong.getGenre()).isEqualTo(genre);
        assertThat(oneSong.getAuthor()).isEqualTo(author);
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