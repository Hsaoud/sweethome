package com.sweet.home.sweethome.repository;

import com.sweet.home.sweethome.model.Cleaner;
import com.sweet.home.sweethome.model.Homer;
import com.sweet.home.sweethome.model.Booking;
import com.sweet.home.sweethome.model.BookingStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookingFlowRepositoriesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CleanerRepository cleanerRepository;

    @Autowired
    private BookingRepository bookingRepository; // To be created

    @Test
    public void testCleanerGeoSpatialSearchAndPricingFields() {
        Cleaner cleaner = new Cleaner();
        cleaner.setEmail("cleaner_geo@test.com");
        cleaner.setPassword("password");
        cleaner.setFirstName("John");
        cleaner.setLastName("Doe");
        // new fields to be implemented
        cleaner.setPricePerSqm(new BigDecimal("15.50"));
        cleaner.setActionRadiusKm(30);
        cleaner.setLatitude(48.8566); // Paris
        cleaner.setLongitude(2.3522);

        entityManager.persistAndFlush(cleaner);

        // Test the new properties are saved correctly
        Cleaner saved = cleanerRepository.findById(cleaner.getId()).orElse(null);
        assertThat(saved).isNotNull();
        assertThat(saved.getPricePerSqm()).isEqualByComparingTo("15.50");
        assertThat(saved.getActionRadiusKm()).isEqualTo(30);

        // Find by radius (distance < 30km)
        // Let's say user is at (48.8600, 2.3500) which is very close.
        List<Cleaner> nearbyCleaners = cleanerRepository.findCleanersWithinRadius(48.8600, 2.3500);
        assertThat(nearbyCleaners).contains(saved);

        // Let's say user is at (43.2965, 5.3698) which is Marseille (far away > 30km).
        List<Cleaner> farCleaners = cleanerRepository.findCleanersWithinRadius(43.2965, 5.3698);
        assertThat(farCleaners).doesNotContain(saved);
    }

    @Test
    public void testBookingRepository() {
        Homer homer = new Homer();
        homer.setEmail("homer_booking@test.com");
        homer.setPassword("password");
        homer.setFirstName("Homer");
        homer.setLastName("Simpson");
        homer.setLatitude(48.8566);
        homer.setLongitude(2.3522);
        entityManager.persist(homer);

        Cleaner cleaner = new Cleaner();
        cleaner.setEmail("cleaner_booking@test.com");
        cleaner.setPassword("password");
        cleaner.setFirstName("Marge");
        cleaner.setLastName("Simpson");
        cleaner.setPricePerSqm(new BigDecimal("20.00"));
        cleaner.setActionRadiusKm(10);
        entityManager.persist(cleaner);

        Booking booking = new Booking();
        booking.setHomer(homer);
        booking.setCleaner(cleaner);
        booking.setDate(LocalDate.of(2026, 6, 1));
        booking.setStartTime(LocalTime.of(10, 0));
        booking.setEndTime(LocalTime.of(12, 0));
        booking.setSurface(50);
        booking.setTotalPrice(new BigDecimal("1000.00")); // 50 * 20
        booking.setStatus(BookingStatus.PENDING);
        entityManager.persistAndFlush(booking);

        // Test repository queries
        List<Booking> homerBookings = bookingRepository.findByHomerId(homer.getId());
        assertThat(homerBookings).hasSize(1);
        assertThat(homerBookings.get(0).getTotalPrice()).isEqualByComparingTo("1000.00");

        List<Booking> cleanerBookings = bookingRepository.findByCleanerId(cleaner.getId());
        assertThat(cleanerBookings).hasSize(1);
        assertThat(cleanerBookings.get(0).getStatus()).isEqualTo(BookingStatus.PENDING);
    }
}
