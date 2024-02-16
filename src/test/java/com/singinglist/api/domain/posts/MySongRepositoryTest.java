package com.singinglist.api.domain.posts;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest //h2 데이터베이스를 자동으로 실행함
public class MySongRepositoryTest {

    @Autowired
    MySongRepository mySongRepository;

    @AfterEach //test 실행되고 난 후
    public void cleanup() {
        mySongRepository.deleteAll();
    }

    @Test
    public void save_test() {
        //given
        String title = "fire";
        String genre = "댄스";
        String author = "2ne1";

        //데이블 posts에 insert/update 쿼리 실행
        mySongRepository.insertSong(MySong.builder().title(title).genre(genre).author(author).build());

        //when
        List<MySong> mySongList = mySongRepository.findAll(); //모든 데이터를 조회해오는 메소드

        //then
        MySong mySong = mySongList.get(0);
        assertThat(mySong.getTitle()).isEqualTo(title);
        assertThat(mySong.getGenre()).isEqualTo(genre);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2023, 1, 15, 0, 0, 0);
        mySongRepository.insertSong(MySong.builder().title("title").genre("genre").author("author").build());
        //when
        List<MySong> mySongList = mySongRepository.findAll();
        //then
        MySong mySong = mySongList.get(0);

        System.out.println(">>>>>>>>>> createDate=" + mySong.getCreatedDate() + ", modifiedDate=" + mySong.getModifiedDate());

        assertThat(mySong.getCreatedDate()).isAfter(now);
        assertThat(mySong.getModifiedDate()).isAfter(now);
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
        List<MySong> mySongList = mySongRepository.findAll();
        MySong oneSong = mySongList.get(0);

        //then
        assertThat(oneSong.getTitle()).isEqualTo(title);
        assertThat(oneSong.getAuthor()).isEqualTo(author);
        assertThat(oneSong.getGenre()).isEqualTo(genre);
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
        List<MySong> findSongList = mySongRepository.findByTitle(title2);
        MySong findOneSong = findSongList.get(0);

        //then
        assertThat(findOneSong.getTitle()).isEqualTo(title2);
        assertThat(findOneSong.getAuthor()).isEqualTo(author2);
        assertThat(findOneSong.getGenre()).isEqualTo(genre2);
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
        //when
        List<MySong> findSongList = mySongRepository.findByTitle(title2);
        Long findSongId = findSongList.get(0).getId();

        mySongRepository.deleteSongById(findSongId);

        Optional<MySong> deletedSong = mySongRepository.findById(findSongId);
        Boolean isDeleted = deletedSong.isEmpty();

        //then
        assertThat(isDeleted).isEqualTo(true);

    }


}
