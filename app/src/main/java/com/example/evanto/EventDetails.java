package com.example.evanto;

public class EventDetails {
    String name,email,guestsCount,phoneNumber,date,location;

    public EventDetails(String name, String email, String guestsCount, String phoneNumber, String date, String location) {
        this.name = name;
        this.email = email;
        this.guestsCount = guestsCount;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGuestsCount() {
        return guestsCount;
    }

    public void setGuestsCount(String guestsCount) {
        this.guestsCount = guestsCount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EventDetails() {
    }
}
