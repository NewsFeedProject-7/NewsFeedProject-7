package org.example.newsfeedproejct.user.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.newsfeedproejct.user.validation.validator.PasswordValidator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {

    String message() default "비밀번호는 대소문자 영문, 숫자, 특수문자를 최소 1개 이상 포함하고 8자 이상이어야 합니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
