package com.oocl.cultivation.test;

import com.oocl.cultivation.Car;
import com.oocl.cultivation.ParkingBoy;
import com.oocl.cultivation.ParkingLot;
import com.oocl.cultivation.ParkingTicket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
         out = new ByteArrayOutputStream();
         System.setOut(new PrintStream(out));
    }

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

        fillParkingLot(parkingBoy);

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

    @Test
    public void should_create_error_message_when_parking_boy_is_given_no_ticket() {
        ParkingBoy parkingboy = new ParkingBoy(new ParkingLot());

        parkingboy.fetch(null);

        assertTrue(out.toString().contains("Please provide your parking ticket"));
    }

    @Test
    public void should_create_error_message_when_parking_boy_is_given_wrong_ticket() {
        ParkingBoy parkingboy = new ParkingBoy(new ParkingLot());
        Car myCar = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();

        ParkingTicket correctTicket = parkingboy.park(myCar);
        parkingboy.fetch(wrongTicket);

        assertTrue(out.toString().contains("Unrecognized parking ticket"));
    }

    @Test
    public void should_create_error_message_when_attempt_to_park_in_full_lot() {
        ParkingBoy parkingboy = new ParkingBoy(new ParkingLot());
        Car myCar = new Car();

        fillParkingLot(parkingboy);

        parkingboy.park(myCar);

        assertTrue(out.toString().contains("Not enough position"));
    }

    @Test
    public void should_be_able_to_park_in_multiple_parking_lots() {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(new ParkingLot());
        parkingLotList.add(new ParkingLot());
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);

        ParkingTicket zerothTicket = parkingBoy.park(new Car());

        for (int i = 1; i <= 14; i++) {
            parkingBoy.park(new Car());
        }

        ParkingTicket fifteenthTicket = parkingBoy.park(new Car());

        Car fetchedZerothCar = parkingBoy.fetch(zerothTicket);
        Car fetchedTwentiethCar = parkingBoy.fetch(fifteenthTicket);

        assertNotNull(fetchedZerothCar);
        assertNotNull(fetchedTwentiethCar);
    }

    @Test
    public void should_park_in_the_first_parking_lot_with_space() {
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstParkingLotTicket = parkingBoy.park(firstCar);
        ParkingTicket secondParkingLotTicket = parkingBoy.park(secondCar);

        assertEquals(firstParkingLot.fetchCar(firstParkingLotTicket), firstCar);
        assertEquals(secondParkingLot.fetchCar(secondParkingLotTicket), secondCar);
    }

    @Test
    public void should_park_in_the_first_parking_lot_with_space_when_a_car_is_removed_from_a_previously_full_lot() {
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);
        Car firstCar = new Car();

        ParkingTicket firstParkingLotTicket = parkingBoy.park(firstCar);
        parkingBoy.park(new Car());

        assertEquals(firstParkingLot.fetchCar(firstParkingLotTicket), firstCar);
        assertFalse(firstParkingLot.isFull());
        assertTrue(secondParkingLot.isFull());

        Car thirdCar = new Car();
        ParkingTicket thirdParkingTicket = parkingBoy.park(thirdCar);
        assertEquals(firstParkingLot.fetchCar(thirdParkingTicket), thirdCar);
    }

    private void fillParkingLot(ParkingBoy parkingboy) {
        while (true) {
            if(parkingboy.park(new Car()) == null){
                break;
            }
        }
    }
}
