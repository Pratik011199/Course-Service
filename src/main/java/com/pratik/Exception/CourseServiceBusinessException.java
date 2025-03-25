package com.pratik.Exception;

public class CourseServiceBusinessException extends RuntimeException{
    public CourseServiceBusinessException(String message){
        super(message);
    }
}
