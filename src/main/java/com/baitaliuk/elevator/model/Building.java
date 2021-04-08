package com.baitaliuk.elevator.model;

import java.util.*;

public class Building {
    private final int maxFloor;
    private final Map<Integer, List<Passenger>> people = new HashMap<>();

    public Building(int maxFloor) {
        this.maxFloor = maxFloor;
        for (int i = 1; i <= maxFloor; i++) {
            people.put(i, new ArrayList<>());
        }
    }

    public void addPassengersToTheFloor(List<Passenger> passengers, int floor) {
        if ( people.containsKey(floor) && !passengers.isEmpty() ) {
            passengers.forEach( p -> p.chooseDestination(floor, maxFloor));
            people.get(floor).addAll(passengers);
        }
    }

    public void putPassengersToElevator(List<Passenger> passengers, int floor) {
        if ( people.containsKey(floor) ) {
            people.get(floor).removeAll(passengers);
        }
    }

    public boolean isSomeoneOnTheFloor(int floor) {
        boolean isFloorOutOfRange = floor<1 || floor>maxFloor;
        if ( isFloorOutOfRange ) {
            return false;
        } return !people.get(floor).isEmpty();
    }

    public Optional<List<Passenger>> getPeopleFromTheFloor(int floor) {
        if ( people.containsKey(floor) ) {
            return Optional.of( people.get(floor) );
        } return Optional.empty(); // неправильный этаж
    }

    public int getMaxFloor() {
        return maxFloor;
    }
}