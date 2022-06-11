package com.tombtomb.jjfollowerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Follow {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID followerId;
    private UUID followedId;

    private String followerUsername;
    private String followedUsername;
}
