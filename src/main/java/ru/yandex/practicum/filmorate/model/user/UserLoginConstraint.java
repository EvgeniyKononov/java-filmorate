package ru.yandex.practicum.filmorate.model.user;

import ru.yandex.practicum.filmorate.model.film.FilmReleaseDateConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserLoginConstraintValidator.class)
public @interface UserLoginConstraint {
    String message() default "логин не может быть пустым и не должен содержать пробелы";
    Class<?>[] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
