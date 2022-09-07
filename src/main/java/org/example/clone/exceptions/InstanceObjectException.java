package org.example.clone.exceptions;

public class InstanceObjectException extends RuntimeException {
    public InstanceObjectException(String message) {
        super(message);
    }

    public InstanceObjectException(Throwable throwable) {
        super(throwable);
    }
}
