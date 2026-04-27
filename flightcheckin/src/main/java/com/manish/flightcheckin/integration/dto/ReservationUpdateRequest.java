package com.manish.flightcheckin.integration.dto;

public class ReservationUpdateRequest {
    private int id;
    private Boolean checkedIn;
    private int numberOfBags;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    @Override
    public String toString() {
        return "ReservationUpdateRequest{" +
                "id=" + id +
                ", checkedIn=" + checkedIn +
                ", numberOfBags=" + numberOfBags +
                '}';
    }
}
