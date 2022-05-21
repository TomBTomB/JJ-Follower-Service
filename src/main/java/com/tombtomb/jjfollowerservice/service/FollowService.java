package com.tombtomb.jjfollowerservice.service;

import com.tombtomb.jjfollowerservice.dto.FollowDTO;
import com.tombtomb.jjfollowerservice.model.Follow;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface FollowService {
    Follow createFollow(FollowDTO createFollowDTO);

    Page<FollowDTO> getFollowers(UUID followedId, int pageNo, int pageSize);

    Page<FollowDTO> getFollowed(UUID followerId, int pageNo, int pageSize);

    FollowDTO deleteFollow(FollowDTO deleteFollowDTO);
}
