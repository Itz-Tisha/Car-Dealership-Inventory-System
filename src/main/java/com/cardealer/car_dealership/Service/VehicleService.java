package com.cardealer.car_dealership.Service;

import com.cardealer.car_dealership.dto.VehicleRequest;
import com.cardealer.car_dealership.entity.Vehicle;

import java.util.List;

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
	    	  if (request.getPrice() == null || request.getPrice() <= 0) {
	    		    throw new RuntimeException("Price must be greater than zero");
	    		}
	    	  if (request.getQuantity() == null || request.getQuantity() < 0) {
	    		    throw new RuntimeException("Quantity cannot be negative");
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
    public List<Vehicle> getAllVehicles() {

        return repository.findAll();

    }
    public List<Vehicle> searchByMake(String make) {
        return repository.findByMakeIgnoreCase(make);
    }
    
    public List<Vehicle> searchByModel(String model) {
        return repository.findByModelIgnoreCase(model);
    }
    
    public List<Vehicle> searchByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }
    
    public List<Vehicle> searchByPriceRange(Double minPrice,
            Double maxPrice) {

		return repository.findByPriceBetween(minPrice, maxPrice);
		}
    
    public String updateVehicle(Long id, VehicleRequest request) {

        Vehicle vehicle = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vehicle not found"));

        vehicle.setMake(request.getMake());
        vehicle.setModel(request.getModel());
        vehicle.setCategory(request.getCategory());
        vehicle.setPrice(request.getPrice());
        vehicle.setQuantity(request.getQuantity());

        repository.save(vehicle);

        return "Vehicle updated successfully";
    }
    
    
}