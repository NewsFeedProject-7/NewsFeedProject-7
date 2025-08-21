package org.example.newsfeedproejct.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedproejct.board.entity.Board;
import org.example.newsfeedproejct.global.entity.BaseEntity;
import org.example.newsfeedproejct.user.entity.User;
import org.springframework.data.domain.Persistable;

@Getter
@Entity
@Table(name = "board_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardLike extends BaseEntity implements Persistable<BoardLikeId> {

    @EmbeddedId
    private BoardLikeId id;

    @MapsId("boardId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BoardLike(BoardLikeId id, Board board, User user) {
        this.id = id;
        this.board = board;
        this.user = user;
    }

    public static BoardLike of(Board board, User user) {

        return new BoardLike(new BoardLikeId(board.getId(), user.getId()), board, user);
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
