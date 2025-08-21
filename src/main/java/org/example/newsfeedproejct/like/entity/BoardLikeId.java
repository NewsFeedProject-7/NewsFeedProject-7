package org.example.newsfeedproejct.like.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Builder
public class BoardLikeId implements Serializable {

    private Long boardId;
    private Long userId;
}
