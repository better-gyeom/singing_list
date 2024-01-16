package com.singinglist.api.domain.posts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class TestRepository {
    private final JdbcTemplate jdbcTemplate; //라이브러리

    @Autowired
    public TestRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource); //jdbc 객체를 받을 떄 datasource 넣어줘야 함
    }

    private RowMapper<Posts> postsRowMapper(){
        return (rs, rowNum) -> { //rs : query를 날렸을 때 반환된 Map
            Posts posts = new Posts();
            posts.setId(rs.getLong("id"));
            posts.setTitle(rs.getString("title"));
            posts.setContent(rs.getString("content"));
            posts.setAuthor(rs.getString("author"));
            return posts; //posts 객체로 변환
        };
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
}
