package com.cardealer.car_dealership.controller;

import com.cardealer.car_dealership.Service.VehicleService;
import com.cardealer.car_dealership.dto.MessageResponse;
import com.cardealer.car_dealership.dto.RestockRequest;
import com.cardealer.car_dealership.dto.VehicleRequest;
import com.cardealer.car_dealership.entity.Vehicle;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        if (make != null) {
            return ResponseEntity.ok(vehicleService.searchByMake(make));
        }
        if (model != null) {
            return ResponseEntity.ok(vehicleService.searchByModel(model));
        }
        if (category != null) {
            return ResponseEntity.ok(vehicleService.searchByCategory(category));
        }
        if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(
                    vehicleService.searchByPriceRange(minPrice, maxPrice)
            );
        }

        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> addVehicle(
            @RequestBody VehicleRequest request) {

        String message = vehicleService.addVehicle(request);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateVehicle(
            @PathVariable Long id,
            @RequestBody VehicleRequest request) {

        String message = vehicleService.updateVehicle(id, request);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PostMapping("/{id}/purchase")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResponse> purchaseVehicle(
            @PathVariable Long id) {

        String message = vehicleService.purchaseVehicle(id);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteVehicle(
            @PathVariable Long id) {

        String message = vehicleService.deleteVehicle(id);
        return ResponseEntity.ok(new MessageResponse(message));
    }

    @PostMapping("/{id}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> restockVehicle(
            @PathVariable Long id,
            @RequestBody RestockRequest request) {

        String message = vehicleService.restockVehicle(id, request);
        return ResponseEntity.ok(new MessageResponse(message));
    }
}
