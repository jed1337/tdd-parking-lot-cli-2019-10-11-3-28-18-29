package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    @Test
    public void should_park_car_by_parking_boy_and_return_ticket(){
        Car car = new Car();
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        ParkingTicket ticket = parkingBoy.park(car);

        assertNotNull(ticket);
    }

    @Test
    public void should_get_car_if_parking_boy_is_given_ticket(){
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);

        Car car = new Car();
        ParkingTicket ticket = parkingBoy.park(car);
        Car fetchedCar = parkingBoy.fetch(ticket);

        assertNotNull(fetchedCar);
    }

    @Test
    public void should_get_correct_car_by_parking_boy() {
        ParkingLot parkingLot = new ParkingLot();
        ParkingBoy parkingBoy = new ParkingBoy(parkingLot);
        Car firstCar = new Car();
        Car otherCar = new Car();

        parkingBoy.park(firstCar);
        parkingBoy.park(otherCar);
        ParkingTicket ticket = parkingBoy.park(firstCar);
        Car fetchedCar = parkingBoy.fetch(ticket);

        assertSame(fetchedCar, firstCar);
        assertNotSame(fetchedCar, otherCar);
    }

    @Test
    public void should_return_no_car_if_no_ticket_is_given_to_parking_boy() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        Car fetchedCar = parkingBoy.fetch(null);

        assertNull(fetchedCar);
    }

    @Test
    public void should_return_no_car_if_wrong_ticket_is_given_to_parking_boy() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        ParkingTicket wrongTicket = new ParkingTicket();

        Car fetchedCar = parkingBoy.fetch(wrongTicket);

        assertNull(fetchedCar);
    }

    @Test
    public void should_return_no_car_if_parking_boy_is_given_a_used_ticket() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());
        Car myCar = new Car();

        ParkingTicket ticket = parkingBoy.park(myCar);
        parkingBoy.fetch(ticket);

        Car carFetchedByUsedTicket = parkingBoy.fetch(ticket);
        assertNull(carFetchedByUsedTicket);
    }

    @Test
    public void should_not_be_able_to_park_when_parking_lot_is_full() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        for (int i = 0; i < 10; i++) {
            parkingBoy.park(new Car());
        }

        ParkingTicket ticketFromFullParking = parkingBoy.park(new Car());
        assertNull(ticketFromFullParking);
    }


    @Test
    public void should_be_able_to_park_when_a_car_is_fetched_from_a_full_parking_lot() {
        ParkingBoy parkingBoy = new ParkingBoy(new ParkingLot());

        ParkingTicket firstCarTicket = parkingBoy.park(new Car());
        for (int i = 0; i < 9; i++) {
            parkingBoy.park(new Car());
        }

        parkingBoy.fetch(firstCarTicket);

        ParkingTicket ticket = parkingBoy.park(new Car());
        assertNotNull(ticket);
    }
}
