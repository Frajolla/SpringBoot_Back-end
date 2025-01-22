package com.projeto.projeto.exception;

public class DataIntegrityViolationException extends RuntimeException {
    public DataIntegrityViolationException(String pMessage) {
        super(pMessage);
    }
    public DataIntegrityViolationException(String pMessage, Throwable pCause) {
        super(pMessage, pCause);
    }
}