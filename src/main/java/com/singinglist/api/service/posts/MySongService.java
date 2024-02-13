package com.singinglist.api.service.posts;

import com.singinglist.api.domain.posts.MySong;
import com.singinglist.api.domain.posts.MySongRepository;
import com.singinglist.api.web.dto.MySongInsertRequestDto;
import com.singinglist.api.web.dto.MySongResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MySongService {
    @Autowired
    private final MySongRepository mySongRepository;

    //나의 노래리스트로 저장
    public void insert(MySongInsertRequestDto requestDto) {
        mySongRepository.insertSong(requestDto.toEntity());
    }

    //나의 노래리스트 조회
    public List<MySongResponseDto> findAll() {
        List<MySong> mySongList = mySongRepository.findAll();

        ArrayList<MySongResponseDto> songList = new ArrayList<>();
        for (MySong song : mySongList) {
            songList.add(new MySongResponseDto(song));
        }

        return songList;
    }

    //나의 노래리스트 단건 조회
    public List<MySongResponseDto> findByTitle(String title) { //JPA의 영속성 컨텍스트 때문에 쿼리를 날리는 부분이 없다(?)
        List<MySong> mySong = mySongRepository.findByTitle(title);

        ArrayList<MySongResponseDto> songByTitle = new ArrayList<>();
        for (MySong song : mySong) {
            songByTitle.add(new MySongResponseDto(song));
        }
        return songByTitle;
    }

}
