package com.singinglist.api.service.posts;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.domain.posts.MySongRepository;
import com.singinglist.api.web.dto.MySongResponseDto;
import com.singinglist.api.web.dto.MySongSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MySongService {
    @Autowired
    private final MySongRepository mySongRepository;

    //나의 노래리스트로 저장
    public Long save(MySongSaveRequestDto requestDto) {
        return mySongRepository.save(requestDto.toEntity()).getId();
    }

    //나의 노래리스트 조회
    public MySongResponseDto findById(Long id) {
        MySong entity = mySongRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new MySongResponseDto(entity);
    }

    //나의 노래리스트 수정
//    public Long update(Long id, MySongUpdateRequestDto requestDto) { //JPA의 영속성 컨텍스트 때문에 쿼리를 날리는 부분이 없다(?)
//        MySong mySong = mySongRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
//        mySong.update(requestDto.getTitle(), requestDto.getContent());
//
//        return id;
//    }

}
