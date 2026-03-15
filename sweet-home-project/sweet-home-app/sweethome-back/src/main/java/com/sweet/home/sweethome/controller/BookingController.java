package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.BookingRequestDto;
import com.sweet.home.sweethome.model.Booking;
import com.sweet.home.sweethome.model.BookingStatus;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.repository.BookingRepository;
import com.sweet.home.sweethome.service.BookingService;
import com.sweet.home.sweethome.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;
    private final UserService userService;

    public BookingController(BookingService bookingService,
            BookingRepository bookingRepository,
            UserService userService) {
        this.bookingService = bookingService;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBookingRequest(@Valid @RequestBody BookingRequestDto request,
            Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Find homer by email
        User homer = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        Booking booking = bookingService.createBookingRequest(homer.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User cleaner = userService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Authenticated user not found"));

        BookingStatus status = BookingStatus.valueOf(body.get("status"));
        Booking updatedBooking = bookingService.updateBookingStatus(cleaner.getId(), id, status);

        return ResponseEntity.ok(updatedBooking);
    }

    @GetMapping("/homer")
    public ResponseEntity<List<Booking>> getHomerBookings(Authentication authentication) {
        User homer = userService.findByEmail(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(bookingRepository.findByHomerId(homer.getId()));
    }

    @GetMapping("/cleaner")
    public ResponseEntity<List<Booking>> getCleanerBookings(Authentication authentication) {
        User cleaner = userService.findByEmail(authentication.getName()).orElseThrow();
        return ResponseEntity.ok(bookingRepository.findByCleanerId(cleaner.getId()));
    }
}
