package com.singinglist.api.web.dto;

import com.singinglist.api.domain.posts.MySong;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MySongSaveRequestDto {
    private String title;
    private String genre;
    private String author;

    @Builder
    public MySongSaveRequestDto(String title, String genre, String author) {
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    public MySong toEntity() {
        return MySong.builder().title(title).genre(genre).author(author).build();
    }
}
