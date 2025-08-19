package org.example.newsfeedproejct.comment.repository;

import org.example.newsfeedproejct.comment.entity.Comment;
import org.example.newsfeedproejct.global.exception.GlobalException;
import org.example.newsfeedproejct.global.exception.errorcode.CommentErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
