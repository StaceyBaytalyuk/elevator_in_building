package com.baitaliuk.elevator;

import com.baitaliuk.elevator.model.Building;
import com.baitaliuk.elevator.model.Elevator;
import com.baitaliuk.elevator.model.Passenger;
import com.baitaliuk.elevator.view.BuildingConsoleView;
import com.baitaliuk.elevator.view.BuildingView;
import com.baitaliuk.elevator.view.ViewType;

import java.util.ArrayList;
import java.util.List;

public class BuildingGenerator {
    private BuildingView view;
    private Building building;
    private Elevator elevator;
    private int floors;

    // значения по умолчанию
    private int minFloor=5, maxFloor=20;
    private int minPeople=0, maxPeople=10;
    private int elevatorCapacity=5;
    private ViewType viewType = ViewType.CONSOLE;

    public void run() {
        generateBuilding();
        fillBuildingWithPeople();
        elevator.launch();
    }

    private void generateBuilding() {
        floors = randomInt(minFloor, maxFloor);
        building = new Building(floors);
        elevator = new Elevator(building, elevatorCapacity);

        view = new BuildingConsoleView(building, elevator);
        // можно добавить сколько угодно способов представления, например:
        /* if ( viewType == com.baitaliuk.elevator.view.ViewType.GUI ) {
            view = new BuildingGuiView(building, elevator);
        }*/

        elevator.setView(view);
        view.maxFloorInBuilding();
    }

    private void fillBuildingWithPeople() {
        int id = 0;
        for (int i = 1; i <= floors; i++) {
            List<Passenger> people = new ArrayList<>(); // люди на і-том этаже
            int peopleNumber = randomInt(minPeople, maxPeople);
            for (int j = 0; j < peopleNumber; j++) {
                id++;
                Passenger passenger = new Passenger(id);
                passenger.chooseDestination(i, floors);
                people.add(passenger);
            }
            building.addPassengersToTheFloor(people, i);
        }
    }

    private int randomInt(int min, int max) {
        return (int)(Math.random() * (max - min + 1) + min);
    }

    public static class Builder{
        private final BuildingGenerator buildingGenerator;

        public Builder() {
            buildingGenerator = new BuildingGenerator();
        }

        public Builder maxFloor(int maxFloor){
            buildingGenerator.maxFloor = maxFloor;
            return this;
        }

        public Builder minFloor(int minFloor){
            buildingGenerator.minFloor = minFloor;
            return this;
        }

        public Builder maxPeople(int maxPeople){
            buildingGenerator.maxPeople = maxPeople;
            return this;
        }

        public Builder minPeople(int minPeople){
            buildingGenerator.minPeople = minPeople;
            return this;
        }

        public Builder elevatorCapacity(int elevatorCapacity){
            buildingGenerator.elevatorCapacity = elevatorCapacity;
            return this;
        }

        public Builder viewType(ViewType type){
            if ( type == ViewType.CONSOLE ) {
                buildingGenerator.viewType = type;
            } else if ( type == ViewType.GUI ) {
                System.out.println("GUI is currently unavailable. Console view will be used.");
            }
            return this;
        }

        public BuildingGenerator build() {
            return buildingGenerator;
        }
    }
}