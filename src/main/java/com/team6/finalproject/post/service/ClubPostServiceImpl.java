package com.team6.finalproject.post.service;

import com.team6.finalproject.club.entity.Club;
import com.team6.finalproject.club.repository.ClubRepository;
import com.team6.finalproject.club.service.ClubService;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.common.file.FileUploader;
import com.team6.finalproject.post.dto.PostRequestDto;
import com.team6.finalproject.post.dto.PostResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.post.repository.PostRepository;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ClubPostServiceImpl implements ClubPostService {

    private final PostRepository postRepository;
    private final ClubService clubService;
    private final FileUploader fileUploader;

    // 모집글 선택 조회
    @Override
    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Post post = findPost(postId);
        return new PostResponseDto(post);
    }

    // 모집글 생성
    @Override
    @Transactional
    public PostResponseDto createdPost(PostRequestDto postRequestDto, User user, MultipartFile multipartFile) throws IOException {
        Club club = clubService.findClub(postRequestDto.getClubId());
        String media = uploadMedia(multipartFile);

        Post post = Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .user(user)
                .club(club)
                .media(media)
                .build();

        postRepository.save(post);

        return new PostResponseDto(post);
    }

    // 모집글 수정
    @Override
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto postRequestDto, User user, MultipartFile multipartFile) throws IOException {
        Post post = updateMedia(multipartFile, postId);

        checkedAuthor(post, user);

        post.update(postRequestDto);

        return new PostResponseDto(post);
    }

    // 모집글 삭제
    @Override
    @Transactional
    public void deletePost(Long postId, User user) {
        Post post = findPost(postId);

        checkedAuthor(post, user);

        post.deletePost();
        ResponseEntity.ok().body(new ApiResponseDto("모집글 삭제 완료!", 200));
    }


    // 모집글에 미디어 업로드
    @Transactional
    public String uploadMedia(MultipartFile file) throws IOException {
        return fileUploader.upload(file);
    }

    // 모집글에 미디어 수정
    @Transactional
    public Post updateMedia(MultipartFile file, Long postId) throws IOException {
        Post post = findPost(postId);
        if (post.getMedia() != null) {
            fileUploader.deleteFile(post.getMedia());
        }

        String media = fileUploader.upload(file);
        post.updateMedia(media);
        return post;
    }

    // 게시글 존재 여부 확인
    @Override
    @Transactional(readOnly = true)
    public Post findPost(Long postId) {
        return postRepository.findByActiveId(postId).orElseThrow(() ->
                new IllegalArgumentException("선택한 글은 존재하지 않습니다.")
        );
    }

    // 글 작성자가 본인인지 확인
    public void checkedAuthor(Post post, User user) {
        if (!post.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("본인이 작성한 글이 아닙니다.");
        }
    }

}
