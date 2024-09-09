package com.VehicleService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.VehicleService.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

	

	Optional<Vehicle> findByVehicleNo(String string);
	  Optional<Vehicle> findByVehicleName(String vehicleName);
	void deleteByVehicleNo(String vehicleNo);
	
}
