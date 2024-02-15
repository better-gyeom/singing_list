package com.singinglist.api.service.posts;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.domain.posts.MySongRepository;
import com.singinglist.api.web.dto.MySongInsertRequestDto;
import com.singinglist.api.web.dto.MySongResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MySongServiceTest {

    @Autowired
    MySongRepository mySongRepository;

    @Autowired
    MySongService mySongService;

    @AfterEach //test 실행되고 난 후
    public void cleanup() {
        mySongRepository.deleteAll();
    }

    @Test
    public void 나의_노래리스트에_추가() {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";

        MySongInsertRequestDto mySongInsertRequestDto = new MySongInsertRequestDto();
        mySongInsertRequestDto.setTitle(title);
        mySongInsertRequestDto.setGenre(genre);
        mySongInsertRequestDto.setAuthor(author);

        //when
        mySongService.insert(mySongInsertRequestDto);
        List<MySong> mySongList = mySongRepository.findAll(); //모든 데이터를 조회해오는 메소드

        //then
        MySong mySong = mySongList.get(0);
        assertThat(mySong.getTitle()).isEqualTo(title);
        assertThat(mySong.getGenre()).isEqualTo(genre);
    }

    @Test
    public void 나의_노래리스트_전체조회() {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";
        mySongRepository.insertSong(MySong.builder().title(title).genre(genre).author(author).build());

        String title2 = "drama";
        String genre2 = "k-pop";
        String author2 = "에스파";
        mySongRepository.insertSong(MySong.builder().title(title2).genre(genre2).author(author2).build());

        //when
        List<MySongResponseDto> all = mySongService.findAll();

        //then
        MySongResponseDto mySong = all.get(1);
        assertThat(mySong.getTitle()).isEqualTo(title2);
        assertThat(mySong.getAuthor()).isEqualTo(author2);
    }

    @Test
    public void 나의_노래리스트_제목으로_단건조회() {
        //given
        String title = "Love 119";
        String genre = "k-pop";
        String author = "라이즈";
        mySongRepository.insertSong(MySong.builder().title(title).genre(genre).author(author).build());

        String title2 = "drama";
        String genre2 = "k-pop";
        String author2 = "에스파";
        mySongRepository.insertSong(MySong.builder().title(title2).genre(genre2).author(author2).build());

        //when
        List<MySongResponseDto> findSong = mySongService.findByTitle(title);
        MySongResponseDto oneSong = findSong.get(0);

        //then
        assertThat(oneSong.getTitle()).isEqualTo(title);
        assertThat(oneSong.getAuthor()).isEqualTo(author);
    }


}