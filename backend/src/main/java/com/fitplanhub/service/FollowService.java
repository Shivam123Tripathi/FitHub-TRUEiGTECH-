package com.fitplanhub.service;

import com.fitplanhub.entity.Follow;
import com.fitplanhub.entity.Trainer;
import com.fitplanhub.entity.User;
import com.fitplanhub.repository.FollowRepository;
import com.fitplanhub.repository.TrainerRepository;
import com.fitplanhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// manages follow/unfollow between users and trainers
@Service
public class FollowService {

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    // follow a trainer
    public void followTrainer(Long userId, Long trainerId) {
        // already following?
        if (followRepository.existsByUserIdAndTrainerId(userId, trainerId)) {
            throw new RuntimeException("You are already following this trainer");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        Trainer trainer = trainerRepository.findById(trainerId)
            .orElseThrow(() -> new RuntimeException("Trainer not found"));

        Follow follow = new Follow();
        follow.setUser(user);
        follow.setTrainer(trainer);
        
        followRepository.save(follow);
    }

    // unfollow a trainer
    public void unfollowTrainer(Long userId, Long trainerId) {
        Follow follow = followRepository.findByUserIdAndTrainerId(userId, trainerId)
            .orElseThrow(() -> new RuntimeException("You are not following this trainer"));

        followRepository.delete(follow);
    }

    // get IDs of trainers this user follows
    public List<Long> getFollowedTrainerIds(Long userId) {
        List<Follow> follows = followRepository.findByUserId(userId);
        return follows.stream()
            .map(f -> f.getTrainer().getId())
            .collect(Collectors.toList());
    }

    // check if user follows a specific trainer
    public boolean isFollowing(Long userId, Long trainerId) {
        return followRepository.existsByUserIdAndTrainerId(userId, trainerId);
    }
}
