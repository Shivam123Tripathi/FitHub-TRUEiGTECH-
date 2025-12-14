package com.fitplanhub.service;

import com.fitplanhub.entity.FitnessPlan;
import com.fitplanhub.entity.Subscription;
import com.fitplanhub.entity.User;
import com.fitplanhub.repository.FitnessPlanRepository;
import com.fitplanhub.repository.SubscriptionRepository;
import com.fitplanhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// handles user subscriptions to fitness plans
@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FitnessPlanRepository fitnessPlanRepository;

    // subscribe to a plan (payment simulation)
    public void subscribeToPlan(Long userId, Long planId) {
        // already subscribed?
        if (subscriptionRepository.existsByUserIdAndPlanId(userId, planId)) {
            throw new RuntimeException("You are already subscribed to this plan");
        }

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        FitnessPlan plan = fitnessPlanRepository.findById(planId)
            .orElseThrow(() -> new RuntimeException("Plan not found"));

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setPlan(plan);
        
        subscriptionRepository.save(subscription);
    }

    // check subscription status
    public boolean isSubscribed(Long userId, Long planId) {
        return subscriptionRepository.existsByUserIdAndPlanId(userId, planId);
    }

    // get IDs of plans user has subscribed to
    public List<Long> getSubscribedPlanIds(Long userId) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
            .map(s -> s.getPlan().getId())
            .collect(Collectors.toList());
    }

    // get full subscription details with plan info
    public List<Subscription> getUserSubscriptions(Long userId) {
        return subscriptionRepository.findSubscriptionsWithPlansByUserId(userId);
    }
}
