package com.cowork.util;

public class SlotAlreadyBookedException extends Exception {

    @Override
    public String toString() {
        return "Slot already booked for the selected desk and time.";
    }
}
