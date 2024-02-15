package com.singinglist.api.web.dto;

import com.singinglist.api.domain.posts.MySong;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MySongInsertRequestDto {
    private String title;
    private String genre;
    private String author;
    private LocalDateTime releaseDate;

    @Builder
    public MySongInsertRequestDto(String title, String genre, String author, LocalDateTime releaseDate) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.releaseDate = releaseDate;
    }

    public MySong toEntity() {
        return MySong.builder().title(title).genre(genre).author(author).releaseDate(releaseDate).build();
    }
}
