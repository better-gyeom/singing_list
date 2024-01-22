package com.singinglist.api.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MySongUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    public MySongUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
