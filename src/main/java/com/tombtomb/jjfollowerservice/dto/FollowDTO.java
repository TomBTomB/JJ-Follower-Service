package com.tombtomb.jjfollowerservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FollowDTO {
    private UUID followerId;
    private UUID followedId;
}
