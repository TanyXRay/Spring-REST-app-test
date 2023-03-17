package ru.home.chernyadieva.restapiapp.util.exception;

public class PersonNotCreatedException extends RuntimeException {

    public PersonNotCreatedException(String msg) {
        super(msg);
    }
}
