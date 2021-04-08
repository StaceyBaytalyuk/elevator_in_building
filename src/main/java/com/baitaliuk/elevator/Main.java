package com.baitaliuk.elevator;

import com.baitaliuk.elevator.view.ViewType;

public class Main {
    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        // коротк
        BuildingGenerator buildingGenerator = new BuildingGenerator.Builder()
                .viewType(ViewType.CONSOLE)
                .elevatorCapacity(3)
                .minFloor(3).maxFloor(6)
                .minPeople(0).maxPeople(5).build();

//        BuildingGenerator buildingGenerator = new BuildingGenerator.Builder()
//                .viewType(ViewType.CONSOLE)
//                .elevatorCapacity(5)
//                .minFloor(5).maxFloor(20)
//                .minPeople(0).maxPeople(10).build();

        buildingGenerator.run();
    }
}