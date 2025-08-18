package org.example.newsfeedproejct.user.repository;

import org.example.newsfeedproejct.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
