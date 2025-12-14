package com.fitplanhub.service;

import com.fitplanhub.dto.TrainerResponse;
import com.fitplanhub.dto.TrainerSignupRequest;
import com.fitplanhub.entity.Trainer;
import com.fitplanhub.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// Business logic for trainer operations
@Service
public class TrainerService {

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private FollowService followService;

    // sign up a new trainer - check email, hash password, save to db
    @Transactional
    public Trainer registerTrainer(TrainerSignupRequest signupRequest) {
        // email already taken?
        if (trainerRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // build the trainer object
        Trainer trainer = new Trainer();
        trainer.setFullName(signupRequest.getFullName());
        trainer.setEmail(signupRequest.getEmail());
        
        // hash password before saving
        trainer.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        
        trainer.setYearsOfExperience(signupRequest.getYearsOfExperience());
        trainer.setSpecializations(signupRequest.getSpecializations());
        trainer.setBio(signupRequest.getBio());

        return trainerRepository.save(trainer);
    }
    
    // get all trainers, including follow status for this user
    public List<TrainerResponse> getAllTrainers(Long userId) {
        List<Trainer> trainers = trainerRepository.findAll();
        return trainers.stream()
            .map(trainer -> mapToTrainerResponse(trainer, userId))
            .collect(Collectors.toList());
    }
    
    // search by name or specialization
    public List<TrainerResponse> searchTrainers(String keyword, Long userId) {
        List<Trainer> trainers = trainerRepository.searchByNameOrSpecialization(keyword);
        return trainers.stream()
            .map(trainer -> mapToTrainerResponse(trainer, userId))
            .collect(Collectors.toList());
    }
    
    // convert Trainer entity to response DTO
    private TrainerResponse mapToTrainerResponse(Trainer trainer, Long userId) {
        TrainerResponse response = new TrainerResponse();
        response.setTrainerId(trainer.getId());
        response.setName(trainer.getFullName());
        response.setSpecializations(trainer.getSpecializations());
        response.setExperience(trainer.getYearsOfExperience());
        response.setBio(trainer.getBio());
        
        // check if user follows this trainer
        boolean isFollowing = followService.isFollowing(userId, trainer.getId());
        response.setFollowing(isFollowing);
        
        return response;
    }
}

