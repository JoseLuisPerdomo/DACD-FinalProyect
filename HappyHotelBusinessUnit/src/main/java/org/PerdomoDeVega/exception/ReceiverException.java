package org.PerdomoDeVega.exception;

public class ReceiverException extends Exception{
    private String message;
    private Throwable error;
    public ReceiverException(String message, Throwable error) {
        this.error = error;
        this.message = message;
    }
}
