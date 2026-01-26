package com.sweet.home.sweethome.repository;

import com.sweet.home.sweethome.model.Review;
import com.sweet.home.sweethome.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByRevieweeOrderByCreatedAtDesc(User reviewee);

    List<Review> findByReviewerOrderByCreatedAtDesc(User reviewer);

    List<Review> findByRevieweeIdOrderByCreatedAtDesc(Long revieweeId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.reviewee.id = :userId")
    Double getAverageRatingForUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.reviewee.id = :userId")
    Long countReviewsForUser(@Param("userId") Long userId);

    boolean existsByReviewerAndReviewee(User reviewer, User reviewee);

    // ID-based lookups
    long countByRevieweeId(Long revieweeId);

    boolean existsByReviewerIdAndRevieweeId(Long reviewerId, Long revieweeId);
}
