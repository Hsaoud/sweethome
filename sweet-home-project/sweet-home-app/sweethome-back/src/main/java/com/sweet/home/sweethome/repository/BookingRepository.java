package com.sweet.home.sweethome.repository;

import com.sweet.home.sweethome.model.Booking;
import com.sweet.home.sweethome.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByHomerId(Long homerId);

    List<Booking> findByCleanerId(Long cleanerId);

    List<Booking> findByCleanerIdAndDateAndStatus(Long cleanerId, LocalDate date, BookingStatus status);

}
