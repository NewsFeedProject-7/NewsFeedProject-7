package org.example.newsfeedproejct.like.repository;

import org.example.newsfeedproejct.like.entity.CommentLike;
import org.example.newsfeedproejct.like.entity.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, CommentLikeId> {

    boolean existsById(CommentLikeId id);

    void deleteById(CommentLikeId id);
}
