package com.tombtomb.jjfollowerservice.service.impl;

import com.tombtomb.jjfollowerservice.dto.FollowDTO;
import com.tombtomb.jjfollowerservice.model.Follow;
import com.tombtomb.jjfollowerservice.repository.FollowRepository;
import com.tombtomb.jjfollowerservice.service.FollowService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class FollowServiceImpl implements FollowService {

    private final Logger logger = Logger.getLogger(FollowServiceImpl.class.getName());

    private final FollowRepository followRepository;

    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public Follow createFollow(FollowDTO createFollowDTO) {
        logger.info("Creating new follow between: "+ createFollowDTO.getFollowerId() + " - "+ createFollowDTO.getFollowedId());
        Follow follow = Follow.builder()
                .followerId(createFollowDTO.getFollowerId())
                .followedId(createFollowDTO.getFollowedId())
                .build();
        logger.info("Follow created: "+ follow.getId());
        return followRepository.save(follow);
    }

    @Override
    public Page<FollowDTO> getFollowers(UUID followedId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        logger.info("Getting all followed by: "+ followedId);
        return followRepository.findAllByFollowedId(followedId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public Page<FollowDTO> getFollowed(UUID followerId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        logger.info("Getting all followers for: "+ followerId);
        return followRepository.findAllByFollowerId(followerId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public FollowDTO deleteFollow(FollowDTO deleteFollowDTO) {
        logger.info("Deleting follow : "+ deleteFollowDTO.getFollowerId() +" - "+ deleteFollowDTO.getFollowedId());
        Follow follow = followRepository.findByFollowerIdAndFollowedId(
                deleteFollowDTO.getFollowerId(), deleteFollowDTO.getFollowedId()
        ).orElseThrow(() -> new RuntimeException("Follow not found"));
        followRepository.delete(follow);
        logger.info("Follow "+ follow.getId()+ " deleted");
        return mapToDTO(follow);
    }

    private FollowDTO mapToDTO(Follow follow) {
        return FollowDTO.builder()
                .followerId(follow.getFollowerId())
                .followedId(follow.getFollowedId())
                .build();
    }
}
