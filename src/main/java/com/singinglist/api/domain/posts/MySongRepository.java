package com.singinglist.api.domain.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MySongRepository {
    private final JdbcTemplate jdbcTemplate; //라이브러리

    @Autowired
    public MySongRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); //jdbc 객체를 받을 떄 datasource 넣어줘야 함
    }

    private RowMapper<MySong> postsRowMapper() {
        return (rs, rowNum) -> { //rs : query를 날렸을 때 반환된 Map
            MySong mySong = new MySong();
            mySong.setId(rs.getLong("id"));
            mySong.setTitle(rs.getString("title"));
            mySong.setGenre(rs.getString("genre"));
            mySong.setAuthor(rs.getString("author"));
            mySong.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
            mySong.setModifiedDate(new Timestamp(rs.getDate("modified_date").getTime()).toLocalDateTime());
            return mySong; //posts 객체로 변환
        };
    }

    //나의 노래리스트로 저장
    public void insertSong(MySong mySong) {
        jdbcTemplate.update("insert into my_song (title, genre, author, release_date, created_date, modified_date)" +
                        " values (?, ?, ?, ?, ?, ?)",
                mySong.getTitle(), mySong.getGenre(), mySong.getAuthor(), mySong.getReleaseDate(), LocalDateTime.now(), LocalDateTime.now());
    }

    //나의 노래리스트 조회
    public List<MySong> findAll() {
        List<MySong> mySongList = jdbcTemplate.query(
                "select * from my_song",
                postsRowMapper());
        return mySongList;
    }

    //나의 노래리스트 제목으로 단건 조회
    public List<MySong> findByTitle(String title) {
        List<MySong> mySong = jdbcTemplate.query("select * from my_song where title = ?",
                postsRowMapper(), title);

        return mySong;
    }


    public Optional<MySong> findById(Long id) { //기존 JPA의 findById를 변경
//        System.out.println("===========================");
//        Map<String, Object> map = jdbcTemplate.queryForMap("select * from posts where id = " + id);
//        map.forEach((k,v) -> System.out.println(k + ":" + v));
//        System.out.println("===========================");

        List<MySong> result = jdbcTemplate.query("select * from my_song where id = ?", postsRowMapper(), id);
        //query를 날릴 때 postsRowMapper가 반환값을 posts객체로 변환시킴
        //여러개를 모두 변환시키기 때문에 List가 된다.

        return result.stream().findAny(); //List 형태의 result를 Optional 형태로 변환하여 반환
    }


    public void deleteAll() {
        jdbcTemplate.update("delete from my_song");
    }
}
