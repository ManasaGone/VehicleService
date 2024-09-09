package com.VehicleService.exceptionHandling;

public class InvalidVehicleDataException extends RuntimeException {

    public InvalidVehicleDataException(String message) {
        super(message);
    }
}
