package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public class ParkingBoy extends AbstractParkingBoy {
    public ParkingBoy(ParkingLot parkingLot) {
        super(Collections.singletonList(parkingLot));
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
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
}
