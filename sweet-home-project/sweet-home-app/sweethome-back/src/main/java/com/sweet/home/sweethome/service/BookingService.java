package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.dto.BookingRequestDto;
import com.sweet.home.sweethome.model.Booking;
import com.sweet.home.sweethome.model.BookingStatus;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.repository.BookingRepository;
import com.sweet.home.sweethome.repository.CleanerRepository;
import com.sweet.home.sweethome.repository.HomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;
    private final HomerRepository homerRepository;

    public BookingService(BookingRepository bookingRepository,
            CleanerRepository cleanerRepository,
            HomerRepository homerRepository) {
        this.bookingRepository = bookingRepository;
        this.cleanerRepository = cleanerRepository;
        this.homerRepository = homerRepository;
    }

    @Transactional
    public Booking createBookingRequest(Long homerId, BookingRequestDto request) {
        Homer homer = homerRepository.findById(homerId)
                .orElseThrow(() -> new RuntimeException("Homer not found"));

        Cleaner cleaner = cleanerRepository.findById(request.getCleanerId())
                .orElseThrow(() -> new RuntimeException("Cleaner not found"));

        Booking booking = new Booking();
        booking.setHomer(homer);
        booking.setCleaner(cleaner);
        booking.setDate(request.getDate());
        booking.setStartTime(request.getStartTime());
        booking.setEndTime(request.getEndTime());
        booking.setSurface(request.getSurface());

        // Calculate price dynamically at booking time
        BigDecimal totalPrice = cleaner.getPricePerSqm().multiply(new BigDecimal(request.getSurface()));
        booking.setTotalPrice(totalPrice);

        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking updateBookingStatus(Long cleanerId, Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getCleaner().getId().equals(cleanerId)) {
            throw new RuntimeException("Unauthorized: Booking belongs to another cleaner");
        }

        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
