package org.example.newsfeedproejct.global.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class SoftDeletableEntity extends BaseEntity {

    private LocalDateTime deleted_At;

    public void softDelete() {
        this.deleted_At = LocalDateTime.now();
    }

}
