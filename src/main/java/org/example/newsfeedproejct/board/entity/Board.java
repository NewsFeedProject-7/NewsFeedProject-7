package org.example.newsfeedproejct.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.newsfeedproejct.global.entity.SoftDeletableEntity;
import org.example.newsfeedproejct.user.entity.User;
import org.springframework.util.ObjectUtils;

@Getter
@Entity
@Table(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int likeCount = 0;

    public Board(String subject, String content, User user) {
        this.subject = subject;
        this.content = content;
        this.user = user;
    }

    public boolean isOwnedBy(Long userId) {

        return ObjectUtils.nullSafeEquals(this.user.getId(), userId);
    }

    public void updateBoard(String subject, String content) {
        if (subject != null) this.subject = subject;
        if (content != null) this.content = content;
    }
}
