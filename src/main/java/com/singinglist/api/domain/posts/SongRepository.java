package com.singinglist.api.domain.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public class SongRepository {
    private final JdbcTemplate jdbcTemplate; //라이브러리

    @Autowired
    public SongRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); //jdbc 객체를 받을 떄 datasource 넣어줘야 함
    }

    private RowMapper<Song> postsRowMapper() {
        return (rs, rowNum) -> { //rs : query를 날렸을 때 반환된 Map
            Song Song = new Song();
            Song.setId(rs.getLong("id"));
            Song.setTitle(rs.getString("title"));
            Song.setGenre(rs.getString("genre"));
            Song.setAuthor(rs.getString("author"));
            Song.setReleaseDate(new Timestamp(rs.getDate("release_date").getTime()).toLocalDateTime());
            Song.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
            Song.setModifiedDate(new Timestamp(rs.getDate("modified_date").getTime()).toLocalDateTime());
            return Song; //posts 객체로 변환
        };
    }

    //노래 검색
    public Optional<Song> findSongByTitle(String title) {
        List<Song> song = jdbcTemplate.query("select * from song where title = ?", postsRowMapper(), title);
        return song.stream().findAny();
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from song");
    }

}
