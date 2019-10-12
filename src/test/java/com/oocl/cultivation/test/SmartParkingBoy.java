package com.oocl.cultivation.test;

import com.oocl.cultivation.AbstractParkingBoy;
import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;

import java.util.Collections;
import java.util.List;

public class SmartParkingBoy extends AbstractParkingBoy {
    public SmartParkingBoy(ParkingLot parkingLot) {
        super(Collections.singletonList(parkingLot));
    }

    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    public ParkingTicket park(Car car) {
        boolean allParkingLotsAreFull = parkingLotList.stream()
                .allMatch(ParkingLot::isFull);

        if(!allParkingLotsAreFull){
            return getMoreSpaceParkingLot().park(car);
        }
        else{
            lastErrorMessage = "Not enough position";
            return null;
        }
    }

    private ParkingLot getMoreSpaceParkingLot() {
        return parkingLotList.stream()
            .sorted((p1, p2) -> Integer.compare(p2.getNumberOfEmptyParkingSpace(), p1.getNumberOfEmptyParkingSpace()))
            .findFirst()
            .get();
    }
}
