package com.tombtomb.jjfollowerservice.repository;

import com.tombtomb.jjfollowerservice.model.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {
    Page<Follow> findAllByFollowedId(UUID followedId, Pageable pageable);

    Page<Follow> findAllByFollowerId(UUID followerId, Pageable pageable);

    Optional<Follow> findByFollowerIdAndFollowedId(UUID followerId, UUID followedId);

    Follow findByFollowerUsernameAndFollowedId(String loggedUser, UUID followedId);
}
