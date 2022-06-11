package com.tombtomb.jjfollowerservice.controller;

import com.tombtomb.jjfollowerservice.dto.FollowDTO;
import com.tombtomb.jjfollowerservice.service.FollowService;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/follow")
@Timed("follow_controller_time")
public class FollowController {
    private final FollowService followService;
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping
    public ResponseEntity<?> createFollow(@Valid @RequestBody FollowDTO followCreateDTO) {
         val follow = followService.createFollow(followCreateDTO);
         return ResponseEntity.ok(follow);
    }

    @GetMapping("/followers/{followedId}")
    public ResponseEntity<?> getFollowersFor(
            @PathVariable UUID followedId,
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
            ) {
        val follow = followService.getFollowers(followedId, pageNo, pageSize);
        return ResponseEntity.ok(follow);
    }

    @GetMapping("/followed/{followerId}")
    public ResponseEntity<?> getFollowedFor(
            @PathVariable UUID followerId,
            @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize
            ) {
        val follow = followService.getFollowed(followerId, pageNo, pageSize);
        return ResponseEntity.ok(follow);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFollow(@Valid @RequestBody FollowDTO followDTO) {
        val follow = followService.deleteFollow(followDTO);
        return ResponseEntity.ok(follow);
    }

}
