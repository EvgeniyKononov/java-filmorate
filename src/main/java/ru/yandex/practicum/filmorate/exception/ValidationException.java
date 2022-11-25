package ru.yandex.practicum.filmorate.exception;

public class ValidationException extends RuntimeException {
    private final String msg;

    public ValidationException(String msg) {
        this.msg = msg;
    }

}
