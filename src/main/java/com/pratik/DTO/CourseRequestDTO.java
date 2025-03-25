package com.pratik.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pratik.Annotation.CourseTypeValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDTO {

   @NotBlank(message = "Course name should not be null")
    private String name;
   @NotEmpty(message = "Trainer name should be defined")
    private String trainerName;
   @NotNull(message = "duartion need to be specified")
    private String duration;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "start date cannot be before the current date")
    private Date startDate;
    @CourseTypeValidation
    private String courseType; //live or recording
    @Min(value = 1500,message = "course price cannot be less than 1500")
    @Max(value = 5000,message = "course price cannot be more than 5000")
    private double fees;
    private boolean isCertificateAvailable;
    @NotEmpty(message = "description must be present")
    @Length(min = 5 , max = 20)
    private String description;
    @Email(message = "invalid email id")
    private String emailId;
    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;

}
