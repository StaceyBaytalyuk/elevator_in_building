package com.baitaliuk.elevator.view;

import com.baitaliuk.elevator.model.Building;
import com.baitaliuk.elevator.model.Elevator;
import com.baitaliuk.elevator.model.Passenger;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BuildingConsoleView implements BuildingView {
    private final Scanner scanner = new Scanner(System.in);
    private Building building;
    private Elevator elevator;
    private int floors;

    public BuildingConsoleView(Building building, Elevator elevator) {
        this.elevator = elevator;
        this.building = building;
        this.floors = building.getMaxFloor();
    }

    public void exitProgramDialog() {
        System.out.print("Enter any key to continue, 0 to stop: ");
        if ( scanner.next().equals("0") ) {
            System.out.println("Finishing the program");
            System.exit(0);
        }
    }

    public void drawState() {
        printBorder();
        for (int i = 1; i <= floors; i++) {
            System.out.println("===============");
            System.out.print(i+" floor: ");
            Optional<List<Passenger>> optional = building.getPeopleFromTheFloor(i);
            if ( optional.isPresent() ) {
                List<Passenger> people = optional.get();
                int peopleNumber = people.size();
                System.out.println(peopleNumber+" people");
                people.forEach(p -> System.out.println(p+"  "));
                System.out.println();
            } else {
                System.out.println("0 people");
            }
        }

        System.out.println("curr floor: "+elevator.getCurrentFloor());
        if ( elevator.getDirection() == 1 ) {
            System.out.println("^");
        } else if ( elevator.getDirection() == -1 ) {
            System.out.println("v");
        }

        List<Passenger> passengersInElevator = elevator.getPassengers();
        if ( !passengersInElevator.isEmpty() ) {
            int peopleNumber = passengersInElevator.size();
            System.out.println("passengers in elevator: "+peopleNumber);
            for (Passenger passenger : passengersInElevator) {
                System.out.print(passenger+"  ");
            }
            System.out.println();
        } else {
            System.out.println("elevator is empty");
        }
        printBorder();
        System.out.println("\n\n\n");
    }

    private void printBorder() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    private void showMessage(String msg) {
        System.out.println(msg);
    }

    public void passengerGoesIn(Passenger passenger) {
        showMessage(passenger+" goes in");
    }

    public void passengerGoesOut(Passenger passenger) {
        showMessage(passenger+" goes out");
    }

    public void maxFloorInBuilding() {
        showMessage(floors+" floors in building");
    }

    public void elevatorIsFull() {
        showMessage("Can't take anyone, the elevator is full");
    }

    public void majorityDecidedToGo(int direction) {
        if ( direction == 1 ) {
            showMessage("Majority decided to go up");
        } else {
            showMessage("Majority decided to go down");
        }
    }
}