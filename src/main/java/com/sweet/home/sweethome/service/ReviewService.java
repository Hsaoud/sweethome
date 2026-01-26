package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Review;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.repository.ReviewRepository;
import com.sweet.home.sweethome.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for review management.
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Review> getReviewsForUser(Long userId) {
        return reviewRepository.findByRevieweeIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByReviewer(User reviewer) {
        return reviewRepository.findByReviewerOrderByCreatedAtDesc(reviewer);
    }

    @Transactional(readOnly = true)
    public Double getAverageRating(Long userId) {
        Double avg = reviewRepository.getAverageRatingForUser(userId);
        return avg != null ? avg : 0.0;
    }

    @Transactional(readOnly = true)
    public Long getReviewCount(Long userId) {
        return reviewRepository.countReviewsForUser(userId);
    }

    @Transactional
    public Review createReview(Long reviewerId, Long revieweeId, Integer rating, String comment) {
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new IllegalArgumentException("Reviewer not found"));
        User reviewee = userRepository.findById(revieweeId)
                .orElseThrow(() -> new IllegalArgumentException("Reviewee not found"));

        if (reviewRepository.existsByReviewerAndReviewee(reviewer, reviewee)) {
            throw new IllegalArgumentException("You have already reviewed this user");
        }

        Review review = new Review(reviewer, reviewee, rating, comment);
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public boolean hasReviewed(Long reviewerId, Long revieweeId) {
        User reviewer = userRepository.findById(reviewerId).orElse(null);
        User reviewee = userRepository.findById(revieweeId).orElse(null);
        if (reviewer == null || reviewee == null)
            return false;
        return reviewRepository.existsByReviewerAndReviewee(reviewer, reviewee);
    }
}
