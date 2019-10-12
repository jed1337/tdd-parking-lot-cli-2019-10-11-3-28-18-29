package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public class ParkingBoy {
    private final List<ParkingLot> parkingLotList;
    private String lastErrorMessage;

    public ParkingBoy(ParkingLot parkingLot) {
        this.parkingLotList = Collections.singletonList(parkingLot);
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public ParkingTicket park(Car car) {
        boolean allParkingLotsAreFull = parkingLotList.stream()
                .allMatch(ParkingLot::isFull);

        if(!allParkingLotsAreFull){
            ParkingLot firstParkingLotWithSpace = parkingLotList.stream()
                    .filter(parkingLot -> !parkingLot.isFull())
                    .findFirst()
                    .get();
            return firstParkingLotWithSpace.park(car);
        }
        else{
            lastErrorMessage = "Not enough position";
            return null;
        }
    }

    public Car fetch(ParkingTicket ticket) {
        if (ticket == null) {
            lastErrorMessage = "Please provide your parking ticket";
            return null;
        }

        Car car = fetchCarFromParkingLotList(ticket);

        if (car == null) {
            lastErrorMessage = "Unrecognized parking ticket";
            return null;
        }
        return car;
    }

    private Car fetchCarFromParkingLotList(ParkingTicket ticket) {
        for (ParkingLot parkingLot : parkingLotList) {
            Car fetchedCar = parkingLot.fetchCar(ticket);
            if(fetchedCar!=null){
                return fetchedCar;
            }
        }
        return null;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
