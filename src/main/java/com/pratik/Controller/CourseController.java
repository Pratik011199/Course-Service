package com.pratik.Controller;

import com.pratik.DTO.CourseRequestDTO;
import com.pratik.DTO.CourseResponseDTO;
import com.pratik.DTO.ServiceResponse;
import com.pratik.Service.CourseService;
import com.pratik.util.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//RestController = Controller + Response Body

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    Logger log = LoggerFactory.getLogger(CourseController.class);

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // @RequestMapping(value = "/course", method = RequestMethod.POST)
    @Operation(summary = "Find course by courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "course added successfully",
                    content = {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))
                    }),
            @ApiResponse(responseCode = "400",description = "validation error")

    })
    @PostMapping()
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO) {

        //validate request
        //validate Request Payload(CourseRequestDTO)
        log.info("CourseController:addCourse Request payload: {}", AppUtils.convertObjectToJson(courseRequestDTO));
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>();
            CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
            serviceResponse.setStatus(HttpStatus.CREATED);
            serviceResponse.setResponse(newCourse);
       // return new ServiceResponse<>(HttpStatus.CREATED,newCourse); // 201
        log.info("CourseController:addCourse response: {}", AppUtils.convertObjectToJson(serviceResponse));
        return serviceResponse;
    }

    @Operation(summary = "Fetch all the course objects")
    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses() {
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllcourses();
        return new ServiceResponse<>( HttpStatus.OK,courseResponseDTOS);
    }

    @Operation(summary = "Find course by courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "course found",
                    content = {
                    @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400",description = "course not found with given id")

    })
    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable Integer courseId) {
        CourseResponseDTO courseResponseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTO);
    }

    @Operation(summary = "Find course by courseId")
    @GetMapping("/search/request")
    public ServiceResponse<?> findCourseUsingRequestParam(@RequestParam(required = false) Integer courseId) {
        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK,responseDTO);
    }

    @Operation(summary = "Delete course by courseId")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable Integer courseId){
        log.info("CourseController:deleteCourse deleting a course with id {} ",courseId);
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update course in the system")
    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable Integer courseId, @RequestBody CourseRequestDTO courseRequestDTO){
        log.info("CourseController:updateCourse Request payload: {} and {}", AppUtils.convertObjectToJson(courseRequestDTO),courseId);
        CourseResponseDTO courseResponseDTO = courseService.updateCourse(courseId,courseRequestDTO);
        ServiceResponse<CourseResponseDTO> serviceResponse =  new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
        log.info("CourseController:updateCourse response body: {}", AppUtils.convertObjectToJson(serviceResponse));
        return serviceResponse;
    }

}
