package com.cardealer.car_dealership.Repository;



import com.cardealer.car_dealership.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository
        extends JpaRepository<Vehicle, Long> {

}