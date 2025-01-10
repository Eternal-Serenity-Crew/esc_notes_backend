package com.esc.escnotesbackend.exceptions;

import javax.naming.AuthenticationException;

public class UserEmailNotFoundException extends AuthenticationException {
    public UserEmailNotFoundException(String message) {
        super(message);
    }
}
