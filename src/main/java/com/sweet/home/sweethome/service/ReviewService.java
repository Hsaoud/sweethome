package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.model.Review;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.repository.ReviewRepository;
import com.sweet.home.sweethome.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsForUser(Long userId) {
        return reviewRepository.findByRevieweeIdOrderByCreatedAtDesc(userId);
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByReviewer(User reviewer) {
        return reviewRepository.findByReviewerOrderByCreatedAtDesc(reviewer);
    }

    @Transactional(readOnly = true)
    public double getAverageRating(Long userId) {
        List<Review> reviews = getReviewsForUser(userId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    @Transactional(readOnly = true)
    public long getReviewCount(Long userId) {
        return reviewRepository.countByRevieweeId(userId);
    }

    @Transactional
    public Review createReview(Long reviewerId, Long revieweeId, Integer rating, String comment) {
        User reviewer = userRepository.findById(reviewerId)
                .orElseThrow(() -> new IllegalArgumentException("Reviewer not found"));
        User reviewee = userRepository.findById(revieweeId)
                .orElseThrow(() -> new IllegalArgumentException("Reviewee not found"));

        if (reviewerId.equals(revieweeId)) {
            throw new IllegalArgumentException("Cannot review yourself");
        }

        Review review = new Review();
        review.setReviewer(reviewer);
        review.setReviewee(reviewee);
        review.setRating(rating);
        review.setComment(comment);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public boolean hasReviewed(Long reviewerId, Long revieweeId) {
        // Simple check if a review exists from reviewer to reviewee
        // Assuming repository has this method or we scan
        // For MVP/Optimization, adding method to repo is best, but scanning list works
        // for small data
        // Let's rely on repo method if exists, or just use list
        return reviewRepository.existsByReviewerIdAndRevieweeId(reviewerId, revieweeId);
    }
}
