package com.team6.finalproject.post.service;

import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.post.repository.PostRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubPostServiceImpl implements ClubPostService {

    private final PostRepository postRepository;

    // 모집글 선택 조회
    @Transactional
    public PostResponseDto getPostById(Long postId) {
        Post post = findPost(postId);
        return new PostResponseDto(post);
    }

    // 모집글 생성
    @Override
    @Transactional
    public PostResponseDto createdPost(PostRequestDto postRequestDto, User user) {
        Post post = new Post(postRequestDto,user);
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
        ResponseEntity.ok().body(new ApiResponseDto("모집글 삭제 완료!", HttpStatus.OK.value()));
    }

    @Override
    @Transactional
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.")
        );
    }


}
