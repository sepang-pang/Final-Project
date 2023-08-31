package com.team6.finalproject.comment.service;

import com.team6.finalproject.comment.dto.CommentRequestDto;
import com.team6.finalproject.comment.dto.CommentResponseDto;
import com.team6.finalproject.comment.entity.Comment;
import com.team6.finalproject.comment.repository.CommentRepository;
import com.team6.finalproject.common.dto.ApiResponseDto;
import com.team6.finalproject.post.entity.Post;
import com.team6.finalproject.post.service.ClubPostService;
import com.team6.finalproject.profile.service.ProfileService;
import com.team6.finalproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ClubPostService clubPostService;
    private final ProfileService profileService;

    // 댓글 작성
    @Override
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {
        Post post = clubPostService.findPost(commentRequestDto.getPostId());
        String nickname = profileService.getProfile(user).getNickname();
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .user(user)
                .post(post)
                .nickname(nickname)
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 조회
    @Override
    @Transactional
    public List<CommentResponseDto> readAllComment() {
        List<Comment> comments = commentRepository.findAll();

        return comments.stream().map(CommentResponseDto::new).toList();
    }

    // 댓글 수정
    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = findComment(commentId);

        checkedAuthor(comment, user);

        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteComment(Long commentId, User user) {
        Comment comment = findComment(commentId);

        checkedAuthor(comment, user);

        comment.deleteComment();
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 완료", 200));
    }

    // 댓글 존재 여부 확인
    @Override
    @Transactional(readOnly = true)
    public Comment findComment(Long commentId) {
        return commentRepository.findByActiveId(commentId).orElseThrow(() ->
                new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
    }

    // 본인 댓글인지 확인
    public void checkedAuthor(Comment comment, User user) {
        if (!comment.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("본인이 작성한 댓글이 아닙니다.");
        }
    }
}
