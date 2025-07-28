package edu.unl.cc.jbrew.exception;

public class CredentialInvalidException extends Exception{

    public CredentialInvalidException() {
        super("Credenciales invalidas");
    }

    public CredentialInvalidException(String message) {
        super(message);
    }
}
