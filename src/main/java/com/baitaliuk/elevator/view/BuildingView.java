package com.baitaliuk.elevator.view;

import com.baitaliuk.elevator.model.Passenger;

public interface BuildingView {
    void drawState();
    void exitProgramDialog();
    void passengerGoesIn(Passenger passenger);
    void passengerGoesOut(Passenger passenger);
    void maxFloorInBuilding();
    void elevatorIsFull();
    void majorityDecidedToGo(int direction);
}