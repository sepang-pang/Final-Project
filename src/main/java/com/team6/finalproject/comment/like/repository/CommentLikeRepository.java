package com.team6.finalproject.comment.like.repository;

import com.team6.finalproject.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long>, CommentLikeRepositoryCustom {

}
