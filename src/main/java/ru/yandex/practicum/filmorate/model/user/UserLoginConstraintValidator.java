package ru.yandex.practicum.filmorate.model.user;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class UserLoginConstraintValidator implements ConstraintValidator<UserLoginConstraint, String> {
    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext){
        return !Objects.equals(null, login) && !login.isBlank() && !login.contains(" ");
    }
}
