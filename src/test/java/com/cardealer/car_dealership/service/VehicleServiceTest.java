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
import java.util.Optional;

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
    
    @Test
    void shouldReturnVehiclesByModel() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setModel("City");

        Mockito.when(repository.findByModelIgnoreCase("City"))
                .thenReturn(List.of(vehicle));

        List<Vehicle> result = service.searchByModel("City");

        assertEquals(1, result.size());
        assertEquals("City", result.get(0).getModel());
    }
    @Test
    void shouldReturnVehiclesByCategory() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setCategory("SUV");

        Mockito.when(repository.findByCategoryIgnoreCase("SUV"))
                .thenReturn(List.of(vehicle));

        List<Vehicle> result = service.searchByCategory("SUV");

        assertEquals(1, result.size());
        assertEquals("SUV", result.get(0).getCategory());
    }
    
    @Test
    void shouldReturnVehiclesWithinPriceRange() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setPrice(2500000.0);

        Mockito.when(repository.findByPriceBetween(2000000.0, 3000000.0))
                .thenReturn(List.of(vehicle));

        List<Vehicle> result =
                service.searchByPriceRange(2000000.0, 3000000.0);

        assertEquals(1, result.size());
        assertEquals(2500000.0, result.get(0).getPrice());
    }
    
    @Test
    void shouldUpdateVehicleSuccessfully() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("SUV");
        vehicle.setPrice(4500000.0);
        vehicle.setQuantity(5);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        VehicleRequest request = new VehicleRequest();
        request.setMake("Toyota");
        request.setModel("Legender");
        request.setCategory("SUV");
        request.setPrice(4800000.0);
        request.setQuantity(10);

        String result = service.updateVehicle(1L, request);

        assertEquals("Vehicle updated successfully", result);

        assertEquals("Legender", vehicle.getModel());
        assertEquals(4800000.0, vehicle.getPrice());
        assertEquals(10, vehicle.getQuantity());

        Mockito.verify(repository).save(vehicle);
    }
    
    @Test
    void shouldThrowExceptionWhenVehicleDoesNotExist() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.empty());

        VehicleRequest request = new VehicleRequest();

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.updateVehicle(1L, request));

        assertEquals("Vehicle not found",
                exception.getMessage());
    }
    
    @Test
    void shouldPurchaseVehicleSuccessfully() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setMake("Toyota");
        vehicle.setQuantity(5);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        String result = service.purchaseVehicle(1L);

        assertEquals("Vehicle purchased successfully", result);

        assertEquals(4, vehicle.getQuantity());

        Mockito.verify(repository).save(vehicle);
    }
    
    @Test
    void shouldThrowExceptionWhenVehicleNotFoundDuringPurchase() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.purchaseVehicle(1L));

        assertEquals("Vehicle not found",
                exception.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenVehicleIsOutOfStock() {

        VehicleRepository repository = Mockito.mock(VehicleRepository.class);

        VehicleService service = new VehicleService(repository);

        Vehicle vehicle = new Vehicle();
        vehicle.setQuantity(0);

        Mockito.when(repository.findById(1L))
                .thenReturn(Optional.of(vehicle));

        RuntimeException exception =
                assertThrows(RuntimeException.class,
                        () -> service.purchaseVehicle(1L));

        assertEquals("Vehicle is out of stock",
                exception.getMessage());

        Mockito.verify(repository, Mockito.never())
                .save(Mockito.any(Vehicle.class));
    }
  
}
