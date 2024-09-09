package com.VehicleService;

import com.VehicleService.entity.Vehicle;
import com.VehicleService.exceptionHandling.InvalidVehicleDataException;
import com.VehicleService.exceptionHandling.ResourceNotFoundException;
import com.VehicleService.exceptionHandling.VehicleAlreadyExistsException;
import com.VehicleService.repository.VehicleRepository;
import com.VehicleService.service.VehicleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VehicleServiceTest {

    @InjectMocks
    private VehicleService vehicleService;

    @Mock
    private VehicleRepository vehicleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddVehicle_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNo("AP36AL3691");
        vehicle.setVehicleName("Vehicle1");
        vehicle.setSeatingCapacity(4);
        vehicle.setDriverId(1L);
        vehicle.setVehicleType("Type1");
        vehicle.setFarePerKm(10.0);

        when(vehicleRepository.findByVehicleNo(vehicle.getVehicleNo())).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle result = vehicleService.addVehicle(vehicle);
        assertNotNull(result);
        assertEquals(vehicle.getVehicleNo(), result.getVehicleNo());
        verify(vehicleRepository, times(1)).save(vehicle);
    }

    @Test
     void testAddVehicle_AlreadyExists() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNo("AP36AL3691");

        when(vehicleRepository.findByVehicleNo(vehicle.getVehicleNo())).thenReturn(Optional.of(vehicle));

        Exception exception = assertThrows(VehicleAlreadyExistsException.class, () -> {
            vehicleService.addVehicle(vehicle);
        });
        assertEquals("Vehicle already exists with ID AP36AL3691", exception.getMessage());
    }

    @Test
     void testDeleteVehicle_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNo("AP36AL3691");

        when(vehicleRepository.findByVehicleNo(vehicle.getVehicleNo())).thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(vehicle.getVehicleNo());

        verify(vehicleRepository, times(1)).delete(vehicle);
    }

    @Test
     void testDeleteVehicle_NotFound() {
        when(vehicleRepository.findByVehicleNo("AP36AL3691")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.deleteVehicle("AP36AL3691");
        });
        assertEquals("Vehicle not found with ID AP36AL3691", exception.getMessage());
    }

  
   

    @Test
     void testGetVehicleByNo_Success() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNo("AP36AL3691");

        when(vehicleRepository.findByVehicleNo(vehicle.getVehicleNo())).thenReturn(Optional.of(vehicle));

        Vehicle result = vehicleService.getVehicleByNo(vehicle.getVehicleNo());
        assertNotNull(result);
        assertEquals(vehicle.getVehicleNo(), result.getVehicleNo());
    }

    @Test
     void testGetVehicleByNo_NotFound() {
        when(vehicleRepository.findByVehicleNo("AP36AL3691")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            vehicleService.getVehicleByNo("AP36AL3691");
        });
        assertEquals("Vehicle not found with ID AP36AL3691", exception.getMessage());
    }
}
