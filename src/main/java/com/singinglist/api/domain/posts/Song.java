package com.singinglist.api.domain.posts;

import com.singinglist.api.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Song extends BaseTimeEntity {
    private Long id;
    private String title; //노래 제목
    private String genre; //노래 장르
    private String author; //노래 가수
    private LocalDateTime releaseDate; //노래 발매일
    
    @Builder
    public Song(String title, String genre, String author, LocalDateTime releaseDate) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.releaseDate = releaseDate;
    }
}
