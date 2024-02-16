package com.singinglist.api.web;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.domain.posts.MySongRepository;
import com.singinglist.api.service.posts.MySongService;
import com.singinglist.api.web.dto.MySongInsertRequestDto;
import com.singinglist.api.web.dto.MySongResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Boolean2DArrayAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
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

    @Test
    public void 나의_노래리스트_제목으로_단건조회() throws Exception {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";
        mySongRepository.insertSong(MySong.builder().title(title).genre(genre).author(author).build());

        String title2 = "drama";
        String genre2 = "k-pop";
        String author2 = "에스파";
        mySongRepository.insertSong(MySong.builder().title(title2).genre(genre2).author(author2).build());

        String url = "http://localhost:" + port + "/api/mysong-list/" + title2;
        //when

        ResponseEntity<MySongResponseDto[]> responseEntity = restTemplate.getForEntity(url, MySongResponseDto[].class);
        MySongResponseDto[] mySongList = responseEntity.getBody();
        System.out.println(mySongList.length);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(mySongList[0].getTitle()).isEqualTo(title2);
        assertThat(mySongList[0].getGenre()).isEqualTo(genre2);
        assertThat(mySongList[0].getAuthor()).isEqualTo(author2);

    }

    @Test
    public void 나의_노래리스트에서_삭제() {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";
        mySongRepository.insertSong(MySong.builder().title(title).genre(genre).author(author).build());

        String title2 = "drama";
        String genre2 = "k-pop";
        String author2 = "에스파";
        mySongRepository.insertSong(MySong.builder().title(title2).genre(genre2).author(author2).build());

        List<MySong> findSongList = mySongRepository.findByTitle(title2);
        Long findSongId = findSongList.get(0).getId();

        String url = "http://localhost:" + port + "/api/mysong-list/delete/" + findSongId;

        //when
        restTemplate.delete(url);
        Optional<MySong> deletedSong = mySongRepository.findById(findSongId);
        Boolean isDeleted = deletedSong.isEmpty();

        //then
        assertThat(isDeleted).isEqualTo(true);
    }


}