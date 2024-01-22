package com.singinglist.api.web.dto;

import com.singinglist.api.domain.posts.MySong;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MySongSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public MySongSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public MySong toEntity() {
        return MySong.builder().title(title).content(content).author(author).build();
    }
}
