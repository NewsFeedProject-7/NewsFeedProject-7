package org.example.newsfeedproejct.board.repository;

import org.example.newsfeedproejct.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
