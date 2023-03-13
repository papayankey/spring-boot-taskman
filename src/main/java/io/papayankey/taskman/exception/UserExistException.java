package io.papayankey.taskman.exception;

public class UserExistException extends RuntimeException {
    public UserExistException(String username, String email) {
        super(String.format("User already exists: {username: %s, email: %s}", username, email));
    }
}
