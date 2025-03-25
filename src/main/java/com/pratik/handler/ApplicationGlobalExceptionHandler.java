package com.pratik.handler;

import com.pratik.DTO.ErrorDTO;
import com.pratik.DTO.ServiceResponse;
import com.pratik.Exception.CourseServiceBusinessException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Hidden
@RestControllerAdvice
public class ApplicationGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ServiceResponse<?> handleMethodArgumentException( MethodArgumentNotValidException exception){
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error-> {
                    ErrorDTO errorDTO = new ErrorDTO(error.getDefaultMessage());
                    errorDTOList.add(errorDTO);
                });
                 serviceResponse.setStatus(HttpStatus.BAD_REQUEST);
                 serviceResponse.setError(errorDTOList);
                  return serviceResponse;
    }
    @ExceptionHandler(CourseServiceBusinessException.class)
    public ServiceResponse<?> handleServiceException(CourseServiceBusinessException exception){
        ServiceResponse<?> serviceResponse = new ServiceResponse<>();
        List<ErrorDTO> errorDTOList = new ArrayList<>();
        errorDTOList.add(new ErrorDTO(exception.getMessage()));
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setError(errorDTOList);
        return  serviceResponse;

    }

}
