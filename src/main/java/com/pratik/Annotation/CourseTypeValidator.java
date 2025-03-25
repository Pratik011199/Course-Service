package com.pratik.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public abstract class CourseTypeValidator implements ConstraintValidator<CourseTypeValidation, String> {
    @Override
    public boolean isValid(String courseType, ConstraintValidatorContext constraintValidatorContext) {
        List list = Arrays.asList("LIVE","RECORDING");
        return list.contains(courseType);
    }
}
