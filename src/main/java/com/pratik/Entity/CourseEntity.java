package com.pratik.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="COURSE_TBL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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

}
