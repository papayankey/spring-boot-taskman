package io.papayankey.taskman.exception;

public class UserExistException extends RuntimeException {

    public UserExistException(String username) {
        super("User already exists: " + username);
    }

}
