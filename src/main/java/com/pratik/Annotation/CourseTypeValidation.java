package com.pratik.Annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CourseTypeValidator.class)
public @interface CourseTypeValidation {
    String message() default "Course type should be either Live or Recording";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
