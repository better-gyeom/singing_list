package com.singinglist.api.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    //DB Layer 접근자
    //인터페이스로 생성 후, JpaRepository<Entity클래스, PK타입>으로 상속하면 기본적인 CRUD 메소드가 자동으로 생성
    //Entity와 Entity Repository는 함께 위치 해야한다.
}
