package com.VehicleService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.VehicleService.entity.Vehicle;
import com.VehicleService.exceptionHandling.ResourceNotFoundException;
import com.VehicleService.exceptionHandling.VehicleAlreadyExistsException;
import com.VehicleService.exceptionHandling.InvalidVehicleDataException;
import com.VehicleService.repository.VehicleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle addVehicle(Vehicle vehicle) {
        Optional<Vehicle> existingVehicle = vehicleRepository.findByVehicleNo(vehicle.getVehicleNo());
        if (existingVehicle.isPresent()) {
            throw new VehicleAlreadyExistsException("Vehicle already exists with ID " + vehicle.getVehicleNo());
        }
        
        if (!isValidVehicle(vehicle)) {
            throw new InvalidVehicleDataException("Invalid vehicle data provided");
        }
        
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(String vehicleNo) {
        Vehicle vehicle = vehicleRepository.findByVehicleNo(vehicleNo)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID " + vehicleNo));
        vehicleRepository.delete(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle updateVehicle(String vehicleNo, Vehicle updatedVehicle) {
        Vehicle vehicle = vehicleRepository.findByVehicleNo(vehicleNo)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID " + vehicleNo));

        if (!isValidVehicle(updatedVehicle)) {
            throw new InvalidVehicleDataException("Invalid vehicle data provided");
        }

        vehicle.setVehicleName(updatedVehicle.getVehicleName());
        vehicle.setSeatingCapacity(updatedVehicle.getSeatingCapacity());
        vehicle.setDriverId(updatedVehicle.getDriverId());
        vehicle.setVehicleType(updatedVehicle.getVehicleType());
        vehicle.setFarePerKm(updatedVehicle.getFarePerKm());

        return vehicleRepository.save(vehicle);
    }

    public Vehicle getVehicleByNo(String vehicleNo) {
        return vehicleRepository.findByVehicleNo(vehicleNo)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with ID " + vehicleNo));
    }

    private boolean isValidVehicle(Vehicle vehicle) {
        // Add validation logic here, e.g., check for null values, etc.
        return vehicle.getVehicleName() != null && vehicle.getSeatingCapacity() > 0;
    }
    public Vehicle getVehicleByName(String vehicleName) {
        return vehicleRepository.findByVehicleName(vehicleName)
            .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for name :: " + vehicleName));
    }
}
