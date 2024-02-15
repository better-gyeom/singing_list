package com.singinglist.api.web.dto;

import com.singinglist.api.domain.posts.MySong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MySongResponseDto {
    private Long id;
    private String title;
    private String genre;
    private String author;
    private LocalDateTime releaseDate;

    public MySongResponseDto(MySong entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.genre = entity.getGenre();
        this.author = entity.getAuthor();
        this.releaseDate = entity.getReleaseDate();
    }
}
