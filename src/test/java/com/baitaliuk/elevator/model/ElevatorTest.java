package com.baitaliuk.elevator.model;

import com.baitaliuk.elevator.view.BuildingConsoleView;
import com.baitaliuk.elevator.view.BuildingView;
import org.junit.gen5.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ElevatorTest {
    Elevator elevator;
    private static final int elevatorCapacity = 5;
    private static final int maxFloor = 5;
    private static final int peopleNumber = 4;
    private static final int currentFloor = 3;

    @org.junit.Test
    @Test
    public void testUpOrDown1() {
        Building building = new Building(maxFloor);
        elevator = new Elevator(elevatorCapacity);
        elevator.setBuilding(building);
        elevator.setCurrentFloor(currentFloor);
        BuildingView view = new BuildingConsoleView(building, elevator);
        elevator.setView(view);

        List<Passenger> passengers = new ArrayList<>();
        for (int i = 1; i < peopleNumber; i++) {
            passengers.add(new Passenger(i, maxFloor)); // 3 человек хочет вверх
        }
        passengers.add(new Passenger(peopleNumber, 1)); // только один вниз

        boolean result = elevator.upOrDown(passengers);
        assertTrue("Result should be true (go up)", result);
    }

    @org.junit.Test
    @Test
    public void testChooseDirectionByMajority2() {
        Building building = new Building(maxFloor);
        elevator = new Elevator(elevatorCapacity);
        elevator.setBuilding(building);
        elevator.setCurrentFloor(currentFloor);
        BuildingView view = new BuildingConsoleView(building, elevator);
        elevator.setView(view);

        List<Passenger> passengers = new ArrayList<>();
        for (int i = 1; i < peopleNumber; i++) {
            passengers.add(new Passenger(i, 1)); // 3 человек хочет вниз
        }
        passengers.add(new Passenger(peopleNumber, maxFloor)); // только один вверх

        boolean result = elevator.upOrDown(passengers);
        assertFalse("Result should be false (go down)", result);
    }
}