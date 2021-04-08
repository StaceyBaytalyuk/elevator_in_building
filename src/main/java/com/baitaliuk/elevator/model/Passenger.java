package com.baitaliuk.elevator.model;

public class Passenger {
    private final int id;
    private int destinationFloor=1; // на какой этаж человеку нужно ехать

    public Passenger(int id) {
        this.id = id;
    }

    public void chooseDestination(int currentFloor, int maxFloor){
        int newFloor;
        do {
            newFloor = (int)(Math.random() * maxFloor + 1);
        } while ( newFloor == currentFloor );
        destinationFloor = newFloor;
    }

    @Override
    public String toString() {
        return destinationFloor + " №" + id;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    // для тестов
    public Passenger(int id, int destinationFloor) {
        this.id = id;
        this.destinationFloor = destinationFloor;
    }
}