package com.myfootballapp.domain.validation;

import com.myfootballapp.domain.exception.DomainException;
import com.myfootballapp.domain.exception.ErrorCodes;
import com.myfootballapp.domain.model.User;

public class UserValidator {

    public void validate(User user) {
        if (!user.getEmail().contains("@")) {
            throw new DomainException("Email no valid", ErrorCodes.USER_INVALID_EMAIL);
        }
    }
}
