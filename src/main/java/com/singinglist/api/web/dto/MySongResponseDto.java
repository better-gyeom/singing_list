package com.singinglist.api.web.dto;

import com.singinglist.api.domain.posts.MySong;
import lombok.Getter;

@Getter
public class MySongResponseDto {
    private Long id;
    private String title;
    private String genre;
    private String author;

    public MySongResponseDto(MySong entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.genre = entity.getGenre();
        this.author = entity.getAuthor();
    }
}
