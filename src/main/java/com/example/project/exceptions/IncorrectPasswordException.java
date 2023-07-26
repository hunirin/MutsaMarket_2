package com.example.project.exceptions;

public class IncorrectPasswordException extends Status400Exception {
    public IncorrectPasswordException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
