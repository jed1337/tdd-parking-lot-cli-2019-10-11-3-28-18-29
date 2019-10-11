package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableParkingPosition() {
        return cars.size() - capacity;
    }

    public ParkingTicket park(Car car) {
        if(cars.size()>=capacity){
            return null;
        }

        ParkingTicket parkingTicket = new ParkingTicket();
        cars.put(parkingTicket, car);
        return parkingTicket;
    }

    public Car fetchCar(ParkingTicket ticket) {
        Car car = cars.get(ticket);
        cars.remove(ticket);
        return car;
    }
}
