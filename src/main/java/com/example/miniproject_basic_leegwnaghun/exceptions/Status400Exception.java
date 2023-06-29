package com.example.miniproject_basic_leegwnaghun.exceptions;

public abstract class Status400Exception extends RuntimeException {
    public Status400Exception(String message) {
        super(message);
    }
}
