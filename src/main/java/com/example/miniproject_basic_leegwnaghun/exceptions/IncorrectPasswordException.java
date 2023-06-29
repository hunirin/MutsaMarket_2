package com.example.miniproject_basic_leegwnaghun.exceptions;

public class IncorrectPasswordException extends Status400Exception {
    public IncorrectPasswordException() {
        super("Passwords do not match");
    }
}
