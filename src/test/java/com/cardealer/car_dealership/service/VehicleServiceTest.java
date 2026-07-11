package com.cardealer.car_dealership.service;



import com.cardealer.car_dealership.dto.VehicleRequest;
import com.cardealer.car_dealership.entity.Vehicle;
import com.cardealer.car_dealership.Repository.VehicleRepository;
import com.cardealer.car_dealership.Service.VehicleService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VehicleServiceTest {

    @Test
    void shouldAddVehicleSuccessfully() {

        VehicleRepository repository =
                Mockito.mock(VehicleRepository.class);

        VehicleService service =
                new VehicleService(repository);

        VehicleRequest request = new VehicleRequest();

        request.setMake("Toyota");
        request.setModel("Fortuner");
        request.setCategory("SUV");
        request.setPrice(4500000.0);
        request.setQuantity(5);

        String result = service.addVehicle(request);

        assertEquals("Vehicle added successfully", result);

        Mockito.verify(repository)
                .save(Mockito.any(Vehicle.class));

    }
    
    @Test
    void shouldThrowExceptionWhenMakeIsEmpty() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        VehicleRequest request = new VehicleRequest();

        request.setMake("");
        request.setModel("Fortuner");
        request.setCategory("SUV");
        request.setPrice(4500000.0);
        request.setQuantity(5);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addVehicle(request));

        assertEquals("Make is required", exception.getMessage());

        Mockito.verify(repository, Mockito.never())
                .save(Mockito.any(Vehicle.class));
    }
    @Test
    void shouldThrowExceptionWhenModelIsEmpty() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        VehicleRequest request = new VehicleRequest();

        request.setMake("Toyota");
        request.setModel("");
        request.setCategory("SUV");
        request.setPrice(4500000.0);
        request.setQuantity(5);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addVehicle(request));

        assertEquals("Model is required", exception.getMessage());
    }
}
