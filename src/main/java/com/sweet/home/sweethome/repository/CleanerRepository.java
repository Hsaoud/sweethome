package com.sweet.home.sweethome.repository;

import com.sweet.home.sweethome.model.Category;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long> {

    List<Cleaner> findByAvailableTrue();

    List<Cleaner> findByCityContainingIgnoreCase(String city);

    @Query("SELECT c FROM Cleaner c JOIN c.categories cat WHERE cat = :category")
    List<Cleaner> findByCategory(@Param("category") Category category);

    @Query("SELECT c FROM Cleaner c JOIN c.skills s WHERE s = :skill")
    List<Cleaner> findBySkill(@Param("skill") Skill skill);

    @Query("SELECT c FROM Cleaner c WHERE c.hourlyRate <= :maxRate")
    List<Cleaner> findByHourlyRateLessThanEqual(@Param("maxRate") BigDecimal maxRate);

    @Query("SELECT DISTINCT c FROM Cleaner c " +
            "WHERE c.available = true " +
            "AND (:city IS NULL OR LOWER(c.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
            "AND (:categoryId IS NULL OR EXISTS (SELECT cat FROM c.categories cat WHERE cat.id = :categoryId))")
    List<Cleaner> searchCleaners(@Param("city") String city,
            @Param("categoryId") Long categoryId);
}
