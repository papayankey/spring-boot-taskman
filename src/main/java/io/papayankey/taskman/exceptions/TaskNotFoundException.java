package io.papayankey.taskman.exceptions;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(int id) {
        super("Task does not exist: " + id);
    }

}
