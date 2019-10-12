package com.oocl.cultivation;

public class ParkingBoy {
    private final ParkingLot parkingLot;
    private String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingTicket park(Car car) {
        return parkingLot.park(car);
    }

    public Car fetch(ParkingTicket ticket) {
        Car car = parkingLot.fetchCar(ticket);
        if (car == null) {
            System.out.println("Unrecognized parking ticket");
        }

        return car;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
