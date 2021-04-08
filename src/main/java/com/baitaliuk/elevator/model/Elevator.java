package com.baitaliuk.elevator.model;

import com.baitaliuk.elevator.view.BuildingView;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private final int capacity;
    private int currentFloor = 1;
    private int direction = -1; // вверх 1, вниз -1
    private final List<Passenger> passengers = new ArrayList<>();
    private Building building;
    private BuildingView view;

    public Elevator(Building building, int capacity) {
        this.building = building;
        this.capacity = capacity;
    }

    public void launch() {
        view.drawState();
        if ( building.isSomeoneOnTheFloor(currentFloor) ) {
            takeFirstPassengers();
        }
        move();
    }

    private void takeFirstPassengers() {
        List<Passenger> allPas = building.getPeopleFromTheFloor(currentFloor).get();
        List<Passenger> passengersToAdd = new ArrayList<>();
        int freePlaces = capacity;

        for (Passenger p : allPas) {
            if ( freePlaces > 0 ) {
                passengersToAdd.add(p);
                freePlaces--;
            } else break;
        }

        passengers.addAll(passengersToAdd);
        building.putPassengersToElevator(passengersToAdd, currentFloor);
    }

    public void move() {
        if ( passengers.isEmpty() ) {
            if ( building.isSomeoneOnTheFloor(currentFloor) ) {
                chooseDirectionByMajority();
                takePeopleInElevator();
            }

        } else {
            if ( doesAnyoneNeedToLeave() ) {
                releasePassengers();
            } else {
                if ( isElevatorFull() ) {
                    view.elevatorIsFull();
                    nextStep();
                }
            }

            if ( building.isSomeoneOnTheFloor(currentFloor) ) {
                takePeopleInElevator();
            }
        }
        nextStep();
    }

    private void nextStep() {
        view.drawState();
        view.exitProgramDialog();

        boolean firstOrLastFloor = currentFloor==1 || currentFloor==building.getMaxFloor();
        if ( firstOrLastFloor ) {
            direction *= -1;
            releasePassengers();
            takePeopleInElevator();
        }

        currentFloor += direction;
        move();
    }

    private boolean doesAnyoneNeedToLeave() {
        return passengers.stream().anyMatch( p -> p.getDestinationFloor() == currentFloor );
    }

    boolean isElevatorFull() {
        return capacity == passengers.size();
    }

    public void chooseDirectionByMajority() {
        List<Passenger> all = building.getPeopleFromTheFloor(currentFloor).get();
        boolean up = upOrDown(all);
        if ( up ) {
            direction = 1;
        } else {
            direction = -1;
        }
        view.majorityDecidedToGo(direction);
    }

    public boolean upOrDown(List<Passenger> passengers) {
        long up = passengers.stream().filter( p -> p.getDestinationFloor()>currentFloor ).count();
        return up >= passengers.size()/2;
    }

    public void takePeopleInElevator() {
        List<Passenger> all = building.getPeopleFromTheFloor(currentFloor).get();
        List<Passenger> passengersToAdd = new ArrayList<>();
        int freePlaces = capacity - passengers.size();

        for (Passenger p : all) {
            if ( freePlaces > 0 ) {
                if ( checkDirection(p) ) {
                    passengersToAdd.add(p);
                    freePlaces--;
                    view.passengerGoesIn(p);
                }
            } else break;
        }

        passengers.addAll(passengersToAdd);
        building.putPassengersToElevator(passengersToAdd, currentFloor);
    }

    // человеку с нами по пути
    private boolean checkDirection(Passenger passenger) {
        if ( direction == 1 ) {
            return passenger.getDestinationFloor() > currentFloor;
        } else {
            return passenger.getDestinationFloor() < currentFloor;
        }
    }

    private void releasePassengers() {
        List<Passenger> releasePas = new ArrayList<>();

        for (Passenger p : passengers) {
            if ( currentFloor == p.getDestinationFloor() ) {
                releasePas.add(p);
                view.passengerGoesOut(p);
            }
        }

        if ( !releasePas.isEmpty() ) {
            passengers.removeAll(releasePas);
            building.addPassengersToTheFloor(releasePas, currentFloor);
        }
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDirection() {
        return direction;
    }

    public void setView(BuildingView view) {
        this.view = view;
    }

    // для тестов
    public Elevator(int capacity) {
        this.capacity = capacity;
    }
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}