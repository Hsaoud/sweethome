package com.sweet.home.sweethome.service;

import com.sweet.home.sweethome.dto.BookingRequestDto;
import com.sweet.home.sweethome.model.Booking;
import com.sweet.home.sweethome.model.BookingStatus;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.repository.BookingRepository;
import com.sweet.home.sweethome.repository.CleanerRepository;
import com.sweet.home.sweethome.repository.HomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CleanerRepository cleanerRepository;

    @Mock
    private HomerRepository homerRepository;

    @InjectMocks
    private BookingService bookingService;

    private Homer homer;
    private Cleaner cleaner;

    @BeforeEach
    void setUp() {
        homer = new Homer();
        homer.setId(1L);

        cleaner = new Cleaner();
        cleaner.setId(2L);
        cleaner.setPricePerSqm(new BigDecimal("15.50"));
    }

    @Test
    void shouldCreateBookingWithCalculatedPriceAndPendingStatus() {
        BookingRequestDto request = new BookingRequestDto();
        request.setCleanerId(2L);
        request.setDate(LocalDate.of(2026, 6, 1));
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(12, 0));
        request.setSurface(50); // 50 Sqm

        when(homerRepository.findById(1L)).thenReturn(Optional.of(homer));
        when(cleanerRepository.findById(2L)).thenReturn(Optional.of(cleaner));

        Booking savedBooking = new Booking();
        savedBooking.setId(10L);
        savedBooking.setTotalPrice(new BigDecimal("775.00")); // 50 * 15.50
        savedBooking.setStatus(BookingStatus.PENDING);

        when(bookingRepository.save(any(Booking.class))).thenReturn(savedBooking);

        Booking result = bookingService.createBookingRequest(1L, request);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(BookingStatus.PENDING);
        assertThat(result.getTotalPrice()).isEqualByComparingTo("775.00");

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void shouldThrowExceptionWhenCleanerNotFound() {
        BookingRequestDto request = new BookingRequestDto();
        request.setCleanerId(99L); // non existent

        when(homerRepository.findById(1L)).thenReturn(Optional.of(homer));
        when(cleanerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookingService.createBookingRequest(1L, request));
    }

    @Test
    void shouldUpdateBookingStatus() {
        Booking booking = new Booking();
        booking.setId(10L);
        booking.setCleaner(cleaner);
        booking.setStatus(BookingStatus.PENDING);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking updated = bookingService.updateBookingStatus(2L, 10L, BookingStatus.ACCEPTED);

        assertThat(updated.getStatus()).isEqualTo(BookingStatus.ACCEPTED);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void shouldThrowExceptionIfSomeoneElseUpdatesBooking() {
        Booking booking = new Booking();
        booking.setId(10L);
        Cleaner differentCleaner = new Cleaner();
        differentCleaner.setId(3L);
        booking.setCleaner(differentCleaner);
        booking.setStatus(BookingStatus.PENDING);

        when(bookingRepository.findById(10L)).thenReturn(Optional.of(booking));

        // Cleaner 2 tries to accept a booking assigned to Cleaner 3
        assertThrows(RuntimeException.class, () -> bookingService.updateBookingStatus(2L, 10L, BookingStatus.ACCEPTED));
    }
}
