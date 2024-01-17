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
public class TestRepository {
    private final JdbcTemplate jdbcTemplate; //라이브러리

    @Autowired
    public TestRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource); //jdbc 객체를 받을 떄 datasource 넣어줘야 함
    }

    private RowMapper<Posts> postsRowMapper() {
        return (rs, rowNum) -> { //rs : query를 날렸을 때 반환된 Map
            Posts posts = new Posts();
            posts.setId(rs.getLong("id"));
            posts.setTitle(rs.getString("title"));
            posts.setContent(rs.getString("content"));
            posts.setAuthor(rs.getString("author"));
            posts.setCreatedDate(new Timestamp(rs.getDate("created_date").getTime()).toLocalDateTime());
            posts.setModifiedDate(new Timestamp(rs.getDate("modified_date").getTime()).toLocalDateTime());
            return posts; //posts 객체로 변환
        };
    }

    public Posts save(Posts posts) {
        Optional<Posts> post = findById(posts.getId()); //id를 가진 post select

        if (post.isPresent()) { //값이 존재하면
            //수정
            jdbcTemplate.update("update posts set title = ?, content = ?, author = ?, modified_date = ? where id = ?",
                    posts.getTitle(), posts.getContent(), posts.getAuthor(), LocalDateTime.now(), posts.getId());
        } else { //값이 없으면
            //삽입
            jdbcTemplate.update("insert into posts (title, content, author, created_date, modified_date) values (?, ?, ?, ?, ?)",
                    posts.getTitle(), posts.getContent(), posts.getAuthor(), LocalDateTime.now(), LocalDateTime.now());
        }
        Posts res = findTitleByAuthor(posts.getTitle(), posts.getAuthor()).orElseThrow();
        return res;
    }

    public Optional<Posts> findById(Long id) { //기존 JPA의 findById를 변경
//        System.out.println("===========================");
//        Map<String, Object> map = jdbcTemplate.queryForMap("select * from posts where id = " + id);
//        map.forEach((k,v) -> System.out.println(k + ":" + v));
//        System.out.println("===========================");

        List<Posts> result = jdbcTemplate.query("select * from posts where id = ?", postsRowMapper(), id);
        //query를 날릴 때 postsRowMapper가 반환값을 posts객체로 변환시킴
        //여러개를 모두 변환시키기 때문에 List가 된다.

        return result.stream().findAny(); //List 형태의 result를 Optional 형태로 변환하여 반환
    }

    public Optional<Posts> findTitleByAuthor(String title, String author) {
        List<Posts> song = jdbcTemplate.query("select * from posts where author = ? and title = ?", postsRowMapper(), author, title);
        return song.stream().findAny();
    }


    public List<Posts> findAll() {
        List<Posts> postsList = jdbcTemplate.query(
                "select * from posts",
                postsRowMapper());
        return postsList;
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from posts");
    }
}
