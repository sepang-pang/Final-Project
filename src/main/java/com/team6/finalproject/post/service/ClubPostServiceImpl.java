package com.team6.finalproject.post.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.post.repository.PostRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ClubPostServiceImpl implements ClubPostService {

    private final PostRepository postRepository;
    private final ClubRepository clubRepository;
    private final FileUploader fileUploader;

    // 모집글 선택 조회
    @Transactional (readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Post post = findPost(postId);
        return new PostResponseDto(post);
    }

    // 모집글 생성
    @Override
    @Transactional
    public PostResponseDto createdPost(PostRequestDto postRequestDto, Long clubId, User user, MultipartFile multipartFile) throws IOException {
        Club club = clubRepository.findById(clubId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 동호회 입니다."));

        // 빌더패턴 도입
        Post post = new Post(postRequestDto, user, club, uploadMedia(multipartFile));
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 모집글 수정
    @Override
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user) {
        Post post = findPost(postId);
        post.update(postRequestDto);
        return new PostResponseDto(post);
    }

    // 모집글 삭제
    @Override
    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = findPost(postId);
        post.deletePost();
        ResponseEntity.ok().body(new ApiResponseDto("모집글 삭제 완료!",200));
    }

    // 모집글에 미디어 업로드
    @Transactional
    public String uploadMedia(MultipartFile file) throws IOException {
        return fileUploader.upload(file);
    }

    // 모집글에 미디어 수정
    @Transactional
    public PostResponseDto updateMedia(MultipartFile file, Long postId) throws IOException {
        Post post = findPost(postId);
        if(post.getMedia() != null) {
            fileUploader.deleteFile(post.getMedia());
        }

        String media = fileUploader.upload(file);
        post.updateMedia(media);
        return new PostResponseDto(post);
    }

    // 게시글 존재 여부 확인
    @Override
    @Transactional
    public Post findPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
    }
}


// 동호회를 개설한 사람 또는 권한이 부여된 사람이 모집글 작성,수정,삭제를 할 수 있다,,,?
// 동호회를 개설한 사람이 해당 동호회 멤버에게 권한을 부여 할 수 있다. (다른 동호회 엠버에게 권한 부여 X)
// 동호회 멤버는 다른사람들에게 권한을 부여할 수 X
// 그렇다면 이것이 멤버가 구성이 되어야지 가능하다?! -> 동호회 가입을 해야한다?!
