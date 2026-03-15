package com.sweet.home.sweethome.controller;

import com.sweet.home.sweethome.dto.CleanerSearchResponseDto;
import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.service.CleanerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CleanerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CleanerService cleanerService;

    @Test
    @WithMockUser
    public void shouldReturnCleanersWithGeospatialFilteringAndCalculatedPrice() throws Exception {
        CleanerSearchResponseDto responseDto = new CleanerSearchResponseDto();
        responseDto.setId(2L);
        responseDto.setFirstName("Marie");
        responseDto.setCalculatedTotalPrice(new BigDecimal("150.00"));

        when(cleanerService.searchCleaners(eq(48.8566), eq(2.3522), eq(50), any(LocalDate.class), any(LocalTime.class),
                any(LocalTime.class)))
                .thenReturn(Collections.singletonList(responseDto));

        mockMvc.perform(get("/api/cleaners/searchGeo")
                .param("lat", "48.8566")
                .param("lng", "2.3522")
                .param("surface", "50")
                .param("date", "2026-06-01")
                .param("startTime", "09:00:00")
                .param("endTime", "12:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].calculatedTotalPrice").value(150.00))
                .andExpect(jsonPath("$[0].firstName").value("Marie"));
    }
}
