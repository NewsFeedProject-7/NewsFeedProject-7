package org.example.newsfeedproejct.like.repository;

import org.example.newsfeedproejct.like.entity.BoardLike;
import org.example.newsfeedproejct.like.entity.BoardLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardLikeRepository extends JpaRepository<BoardLike, BoardLikeId> {

    boolean existsById(BoardLikeId id);

    void deleteById(BoardLikeId id);
}
