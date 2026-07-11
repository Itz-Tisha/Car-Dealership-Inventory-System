package com.cardealer.car_dealership.Repository;



import com.cardealer.car_dealership.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
extends JpaRepository<User,Long>{

    boolean existsByEmail(String email);

}