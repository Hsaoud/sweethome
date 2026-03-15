package com.sweet.home.sweethome.repository;

import com.sweet.home.sweethome.model.Homer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomerRepository extends JpaRepository<Homer, Long> {

    List<Homer> findByCity(String city);

    List<Homer> findByCityContainingIgnoreCase(String city);
}
