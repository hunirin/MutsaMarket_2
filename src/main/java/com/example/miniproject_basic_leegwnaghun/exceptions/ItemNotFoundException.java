package com.example.miniproject_basic_leegwnaghun.exceptions;

public class ItemNotFoundException extends Status404Exception {
    public ItemNotFoundException() {
        super("target item not found");
    }
}
