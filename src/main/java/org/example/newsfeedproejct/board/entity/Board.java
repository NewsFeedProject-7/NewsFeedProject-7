package org.example.newsfeedproejct.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.newsfeedproejct.global.entity.SoftDeletableEntity;
import org.example.newsfeedproejct.user.entity.User;

@Getter
@Entity
@Table(name = "boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Lob
    @Column(nullable = false)
    private String content;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Board(String subject, String content, User user) {
        this.subject = subject;
        this.content = content;
        this.user = user;
    }

    public void updateBoard(String subject, String content) {
        if (subject != null) this.subject = subject;
        if (content != null) this.content = content;
    }
}
