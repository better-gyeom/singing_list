package com.singinglist.api.domain;


import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class BaseTimeEntity {


    private LocalDateTime createdDate;


    private LocalDateTime modifiedDate;
}
