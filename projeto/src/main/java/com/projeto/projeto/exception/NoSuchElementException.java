package com.projeto.projeto.exception;

public class NoSuchElementException extends RuntimeException{
    public NoSuchElementException(String pMessage) {
        super(pMessage);
    }
    public NoSuchElementException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}