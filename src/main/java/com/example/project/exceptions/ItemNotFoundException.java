package com.example.project.exceptions;

public class ItemNotFoundException extends Status404Exception {
    public ItemNotFoundException() {
        super("물품이 없습니다");
    }
}
