package com.singinglist.api.domain.posts;

import com.singinglist.api.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//주요 어노테이션을 클래스에 가깝게 둔다
@Getter //클래스 내 모든 필드의 Getter 메소드 자동생성
@Setter
@NoArgsConstructor //기본 생성자 자동 추가 (public Posts() {}와 같은 효과)
public class MySong extends BaseTimeEntity {

    private Long id;
    private String title; //노래 제목
    private String genre; //노래 장르
    private String author; //노래 가수

    @Builder //해당 클래스의 빌더 패턴 클래스를 생성
    //생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public MySong(String title, String genre, String author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

}
