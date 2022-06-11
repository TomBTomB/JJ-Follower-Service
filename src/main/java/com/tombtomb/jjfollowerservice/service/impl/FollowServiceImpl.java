package com.tombtomb.jjfollowerservice.service.impl;

import com.tombtomb.jjfollowerservice.dto.FollowDTO;
import com.tombtomb.jjfollowerservice.model.Follow;
import com.tombtomb.jjfollowerservice.repository.FollowRepository;
import com.tombtomb.jjfollowerservice.service.FollowService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public void toggleFollow(UUID followedId) {

        Follow follow = followRepository.findByFollowerUsernameAndFollowedId(getLoggedUser(), followedId);
        if(follow != null) {
            followRepository.delete(follow);
            logger.info("User=" + getLoggedUser() + ";Unfollowed="+followedId);
        } else {
            logger.info("User=" + getLoggedUser() + ";Followed="+followedId);
            Follow toSave = Follow.builder()
                    .followerUsername(getLoggedUser())
                    .followedId(followedId)
                    .build();
            Follow save = followRepository.save(toSave);
            logger.info("Follow created="+ save.getId() + ";Follower=" + save.getFollowerUsername() + ";Followed=" + save.getFollowedId());
        }

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

    @Override
    public boolean isFollowed(UUID followedId) {
        return followRepository.findByFollowerUsernameAndFollowedId(getLoggedUser(), followedId) != null;
    }

    private FollowDTO mapToDTO(Follow follow) {
        return FollowDTO.builder()
                .followerId(follow.getFollowerId())
                .followedId(follow.getFollowedId())
                .build();
    }

    private String getLoggedUser() {
        KeycloakPrincipal principal=(KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        return session.getToken().getPreferredUsername();
    }
}
