package com.singinglist.api.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;

import static java.time.Month.NOVEMBER;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest //h2 데이터베이스를 자동으로 실행함
public class SongRepositoryTest {

    private final JdbcTemplate jdbcTemplate; //라이브러리

    @Autowired
    public SongRepositoryTest(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); //jdbc 객체를 받을 떄 datasource 넣어줘야 함
    }

    private RowMapper<Song> postsRowMapper() {
        return (rs, rowNum) -> { //rs : query를 날렸을 때 반환된 Map
            Song song = new Song();
            song.setId(rs.getLong("id"));
            song.setTitle(rs.getString("title"));
            song.setGenre(rs.getString("genre"));
            song.setAuthor(rs.getString("author"));
            song.setReleaseDate(new Timestamp(rs.getDate("release_date").getTime()).toLocalDateTime());
            song.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
            song.setModifiedDate(new Timestamp(rs.getDate("modified_date").getTime()).toLocalDateTime());
            return song; //posts 객체로 변환
        };
    }

    @Autowired
    MySongRepository mySongRepository;
    @Autowired
    SongRepository songRepository;

    @AfterEach
    public void tearDown() throws Exception {
        songRepository.deleteAll();
    }


    @Test
    public void song_타이틀로_찾기() {
        //given
        String title = "drama";
        String genre = "댄스";
        String author = "에스파";
        LocalDateTime releaseDate = LocalDateTime.of(2023, Month.NOVEMBER, 10, 0, 0);

        //when
        jdbcTemplate.update("insert into song (title, genre, author, release_date) values (?, ?, ?, ?)", title, genre, author, releaseDate);

        Song song = songRepository.findSongByTitle(title)
                .orElseThrow(() -> new IllegalArgumentException("해당 노래가 없습니다."));

        //then
        assertThat(song.getTitle()).isEqualTo(title);
    }

}
