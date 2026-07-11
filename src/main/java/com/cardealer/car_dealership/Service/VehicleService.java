package com.cardealer.car_dealership.Service;

import com.cardealer.car_dealership.dto.VehicleRequest;
import com.cardealer.car_dealership.entity.Vehicle;
import com.cardealer.car_dealership.Repository.VehicleRepository;

public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public String addVehicle(VehicleRequest request) {
    	  if (request.getMake() == null || request.getMake().trim().isEmpty()) {
    	        throw new RuntimeException("Make is required");
    	    }
    	  if (request.getModel() == null || request.getModel().trim().isEmpty()) {
    		    throw new RuntimeException("Model is required");
    		}
    	  if (request.getCategory() == null || request.getCategory().trim().isEmpty()) {
    		    throw new RuntimeException("Category is required");
    		}
        Vehicle vehicle = new Vehicle();

        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setCategory(request.getCategory());
        vehicle.setPrice(request.getPrice());
        vehicle.setQuantity(request.getQuantity());

        repository.save(vehicle);

        return "Vehicle added successfully";
    }
}