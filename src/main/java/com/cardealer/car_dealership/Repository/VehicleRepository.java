package com.cardealer.car_dealership.Repository;



import com.cardealer.car_dealership.entity.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {
	List<Vehicle> findByMakeIgnoreCase(String make);
}