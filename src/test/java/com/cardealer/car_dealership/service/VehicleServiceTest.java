package com.cardealer.car_dealership.service;



import com.cardealer.car_dealership.dto.VehicleRequest;
import com.cardealer.car_dealership.entity.Vehicle;
import com.cardealer.car_dealership.Repository.VehicleRepository;
import com.cardealer.car_dealership.Service.VehicleService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

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
    @Test
    void shouldThrowExceptionWhenCategoryIsEmpty() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        VehicleRequest request = new VehicleRequest();

        request.setMake("Toyota");
        request.setModel("Fortuner");
        request.setCategory("");
        request.setPrice(4500000.0);
        request.setQuantity(5);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addVehicle(request));

        assertEquals("Category is required", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenPriceIsInvalid() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        VehicleRequest request = new VehicleRequest();

        request.setMake("Toyota");
        request.setModel("Fortuner");
        request.setCategory("SUV");
        request.setPrice(0.0);
        request.setQuantity(5);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addVehicle(request));

        assertEquals("Price must be greater than zero", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        VehicleRequest request = new VehicleRequest();

        request.setMake("Toyota");
        request.setModel("Fortuner");
        request.setCategory("SUV");
        request.setPrice(4500000.0);
        request.setQuantity(-1);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addVehicle(request));

        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
    @Test
    void shouldReturnAllVehicles() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setMake("Toyota");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setMake("Honda");

        List<Vehicle> vehicles = List.of(vehicle1, vehicle2);

        Mockito.when(repository.findAll())
                .thenReturn(vehicles);

        List<Vehicle> result = service.getAllVehicles();

        assertEquals(2, result.size());

        assertEquals("Toyota", result.get(0).getMake());

        assertEquals("Honda", result.get(1).getMake());

        Mockito.verify(repository).findAll();
    }
    @Test
    void shouldReturnVehiclesByMake() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");

        Mockito.when(repository.findByMakeIgnoreCase("Toyota"))
                .thenReturn(List.of(vehicle));

        List<Vehicle> result = service.searchByMake("Toyota");

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getMake());

        Mockito.verify(repository).findByMakeIgnoreCase("Toyota");
    }
}
