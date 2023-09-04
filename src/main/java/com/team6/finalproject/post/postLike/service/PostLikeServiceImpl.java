package com.team6.finalproject.post.postLike.service;

import com.team6.finalproject.advice.custom.NotExistResourceException;
import com.team6.finalproject.advice.custom.NotLikedYetException;
import com.team6.finalproject.advice.custom.NotOwnedByUserException;
import com.team6.finalproject.advice.custom.SelfLikeNotAllowedException;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.post.postLike.entity.PostLike;
import com.team6.finalproject.post.postLike.repository.PostLikeRepository;
import com.team6.finalproject.post.service.ClubPostService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeServiceImpl implements PostLikeService{
    private final PostLikeRepository postLikeRepository;
    private final ClubPostService clubPostService;

    // 게시물 좋아요
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> postLike(Long postId, User user) throws SelfLikeNotAllowedException, NotExistResourceException {
        Post post = clubPostService.findPost(postId);

        PostLike like = PostLike.builder()
                .post(post)
                .user(user)
                .build();

        if (user.getId().equals(post.getUser().getId())) {
            throw new SelfLikeNotAllowedException("본인 게시글엔 좋아요를 할 수 없습니다.");
        }

        postLikeRepository.save(like);

        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 완료", 200));
    }

    // 게시물 좋아요 취소
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> PostDislike(Long postId, User user) throws NotLikedYetException, NotOwnedByUserException {

        PostLike like = postLikeRepository.findByActivePostId(postId).orElseThrow(() ->
                new NotLikedYetException("좋아요를 누르지 않았습니다."));

        checkedUser(like, user);

        like.dislike();
        return ResponseEntity.ok().body(new ApiResponseDto("좋아요 취소 완료", 200));

    }



    private void checkedUser (PostLike postLike, User user) throws NotOwnedByUserException {
        if (!postLike.getUser().getId().equals(user.getId())) {
            throw new NotOwnedByUserException("본인이 누른 좋아요가 아닙니다.");
        }
    }




}
