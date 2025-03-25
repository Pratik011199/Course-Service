package com.pratik.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponseDTO {

    private int courseId;
    private String name;
    private String trainerName;
    private String duration;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startDate;
    private String courseType; //live or recording
    private double fees;
    private boolean isCertificateAvailable;
    private String description;
    private String emailId;
    private String contact;
    private String courseUniqueCode;


}
