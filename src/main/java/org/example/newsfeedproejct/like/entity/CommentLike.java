package org.example.newsfeedproejct.like.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.newsfeedproejct.comment.entity.Comment;
import org.example.newsfeedproejct.global.entity.BaseEntity;
import org.example.newsfeedproejct.user.entity.User;
import org.springframework.data.domain.Persistable;

@Getter
@Entity
@Table(name = "comment_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentLike extends BaseEntity implements Persistable<CommentLikeId> {

    @EmbeddedId
    private CommentLikeId id;

    @MapsId("commentId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private CommentLike(CommentLikeId id, Comment comment, User user) {
        this.id = id;
        this.comment = comment;
        this.user = user;
    }

    public static CommentLike of(Comment comment, User user) {

        return new CommentLike(new CommentLikeId(comment.getId(), user.getId()), comment, user);
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }
}
