package com.oocl.cultivation.test;

import com.oocl.cultivation.AbstractParkingBoy;
import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;

import java.util.Collections;
import java.util.List;

public class SuperSmartParkingBoy extends AbstractParkingBoy {
    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super(Collections.singletonList(parkingLot));
    }

    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        boolean allParkingLotsAreFull = parkingLotList.stream()
                .allMatch(ParkingLot::isFull);

        if (!allParkingLotsAreFull) {
            return getMoreSpaceRateParkingLot().park(car);
        } else {
            lastErrorMessage = "Not enough position";
            return null;
        }
    }

    private ParkingLot getMoreSpaceRateParkingLot() {
        return parkingLotList.stream()
                .sorted((p1, p2) -> Double.compare(getSpaceRate(p2), getSpaceRate(p1)))
                .findFirst()
                .get();
    }

    private double getSpaceRate(ParkingLot parkingLot) {
        double numberOfEmptyParkingSpace = parkingLot.getNumberOfEmptyParkingSpace();
        double totalCapacity = parkingLot.getCapacity();

        return numberOfEmptyParkingSpace/totalCapacity;
    }
}
