package com.galvanize.autos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Automobile, Long> {
    List<Automobile> findByColorAndMake(String color, String make);
    List<Automobile> findByColor(String color);
    List<Automobile> findByMake(String make);
}
