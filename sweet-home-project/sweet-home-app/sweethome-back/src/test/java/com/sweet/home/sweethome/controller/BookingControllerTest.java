package com.sweet.home.sweethome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweet.home.sweethome.dto.BookingRequestDto;
import com.sweet.home.sweethome.model.Booking;
import com.sweet.home.sweethome.model.BookingStatus;
import com.sweet.home.sweethome.model.User;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.service.BookingService;
import com.sweet.home.sweethome.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "homer@test.com", roles = "USER")
    public void shouldCreateBookingRequest() throws Exception {
        BookingRequestDto request = new BookingRequestDto();
        request.setCleanerId(2L);
        request.setDate(LocalDate.of(2026, 6, 1));
        request.setStartTime(LocalTime.of(10, 0));
        request.setEndTime(LocalTime.of(12, 0));
        request.setSurface(50);

        Booking mockBooking = new Booking();
        mockBooking.setId(10L);
        mockBooking.setStatus(BookingStatus.PENDING);

        User mockHomer = new Homer();
        mockHomer.setId(1L);

        when(userService.findByEmail("homer@test.com")).thenReturn(Optional.of(mockHomer));

        // Since we mock the user context, auth logic provides Homer ID 1L
        when(bookingService.createBookingRequest(any(), any(BookingRequestDto.class))).thenReturn(mockBooking);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    @WithMockUser(username = "cleaner@test.com", roles = "USER")
    public void shouldUpdateBookingStatus() throws Exception {
        Booking mockBooking = new Booking();
        mockBooking.setId(10L);
        mockBooking.setStatus(BookingStatus.ACCEPTED);

        User mockCleaner = new Cleaner();
        mockCleaner.setId(2L);

        when(userService.findByEmail("cleaner@test.com")).thenReturn(Optional.of(mockCleaner));

        when(bookingService.updateBookingStatus(any(), eq(10L), eq(BookingStatus.ACCEPTED))).thenReturn(mockBooking);

        mockMvc.perform(patch("/api/bookings/10/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Map.of("status", "ACCEPTED"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }
}
