package com.VehicleService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.VehicleService.entity.Vehicle;
import com.VehicleService.service.VehicleService;

@RestController
@RequestMapping("/vehicles")
@CrossOrigin("*")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/AddVehicle")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.addVehicle(vehicle);
    }

    @DeleteMapping("/DeleteVehicle/{vehicleNo}")
    public void deleteVehicle(@PathVariable String vehicleNo) {
        vehicleService.deleteVehicle(vehicleNo);
    }

    @GetMapping("/ViewAllVehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/ViewVehicleById/{vehicleNo}")
    public Vehicle getVehicleById(@PathVariable String vehicleNo) {
        return vehicleService.getVehicleByNo(vehicleNo);
    }

    @PutMapping("/UpdateVehicle/{vehicleNo}")
    public Vehicle updateVehicle(@PathVariable String vehicleNo, @RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicleNo, vehicle);
    }
    @GetMapping("/name/{vehicleName}")
    public Vehicle getVehicleByName(@PathVariable("vehicleName") String vehicleName) {
        return vehicleService.getVehicleByName(vehicleName);
    }
}
