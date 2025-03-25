package com.pratik.Service;

import com.pratik.DTO.CourseRequestDTO;
import com.pratik.DTO.CourseResponseDTO;
import com.pratik.Entity.CourseEntity;
import com.pratik.Exception.CourseServiceBusinessException;
import com.pratik.Repository.CourseRepo;
import com.pratik.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CourseService {

    @Autowired
    private CourseRepo courserepo;

    Logger log = LoggerFactory.getLogger(CourseService.class);

    // create course object in DB -> POST
    public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO) {
        //convert DTO to ENTITY
        CourseEntity courseEntity = AppUtils.mapDTOtoEntity(courseRequestDTO);
        CourseEntity entity = null;
        log.info("CourseService::onboardNewCourse method execution started.");
        try {
            entity = courserepo.save(courseEntity);
            log.debug("course entity response from Database {} ", AppUtils.convertObjectToJson(entity));
            //return course;
            //convert ENTITY to DTO
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(entity);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            log.debug("CourseService::onboardNewCourse method response {} ", AppUtils.convertObjectToJson(courseResponseDTO));
            log.info("CourseService::onboardNewCourse method execution ended.");
            return courseResponseDTO;
        } catch (Exception exception) {
            log.error("CourseService::onboardNewCourse exception occurs while persisting the course object to DB .");
            throw new CourseServiceBusinessException("onboardNewCourse method failed");
        }
        // course.setCourseId(new Random().nextInt(3756));
        // courses.add(course);
    }

    //load all courses from Database
    public List<CourseResponseDTO> viewAllcourses() {
        Iterable<CourseEntity> courseEntities = null;
        try {
            courseEntities = courserepo.findAll();
            return StreamSupport.stream(courseEntities.spliterator(), false)
                    .map(AppUtils::mapEntityToDTO)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new CourseServiceBusinessException("viewAllcourses method failed");
        }


    }

    //filter course by courseId //GET
    public CourseResponseDTO findByCourseId(Integer courseId) {
        CourseEntity courseEntity = courserepo.findById(courseId)
                .orElseThrow(() -> new CourseServiceBusinessException(courseId + "does not exist"));
        return AppUtils.mapEntityToDTO(courseEntity);
        //return courses.stream()
        // .filter(course -> course.getCourseId()==courseId)
        // .findFirst().orElse(null);
    }

    //delete course
    public void deleteCourse(int courseId) {
        log.info("CourseService::deleteCourse method execution started ..");
        try {
            log.debug("CourseService::deleteCourse method input {}", courseId);
            courserepo.deleteById(courseId);
            //Course course = findByCourseId(courseId);
            // courses.remove(course);
        } catch (Exception e) {
            log.error("CourseService::onboardNewCourse exception occurs while deleting the course object."+e.getMessage());
            throw new CourseServiceBusinessException("deleteCourse service method failed.."+e.getMessage());
        }
        log.info("CourseService::deleteCourse method execution ended ..");
    }

    //update course
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {
        log.info("CourseService::updateCourse method execution started ..");
        try {
            // get the existing object
            log.debug("CourseService::updateCourse request payload {} & id {} ",AppUtils.convertObjectToJson(courseRequestDTO),courseId);
            CourseEntity existingcourseEntity = courserepo.findById(courseId).orElseThrow(()-> new RuntimeException("course object not present in db with id" +courseId));
            log.debug("CourseService::updateCourse request payload {} & id {} ",AppUtils.convertObjectToJson(existingcourseEntity),courseId);
            //existingcourseEntity.setCourseId(courseRequestDTO.getCourseId());
            // modify existing object
            existingcourseEntity.setName(courseRequestDTO.getName());
            existingcourseEntity.setTrainerName(courseRequestDTO.getTrainerName());
            existingcourseEntity.setDuration(courseRequestDTO.getDuration());
            existingcourseEntity.setStartDate(courseRequestDTO.getStartDate());
            existingcourseEntity.setCourseType(courseRequestDTO.getCourseType());
            existingcourseEntity.setFees(courseRequestDTO.getFees());
            existingcourseEntity.setCertificateAvailable(courseRequestDTO.isCertificateAvailable());
            existingcourseEntity.setDescription(courseRequestDTO.getDescription());
            // save modified value
            CourseEntity updatedcourseentity = courserepo.save(existingcourseEntity);
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(updatedcourseentity);

            log.debug("CourseService::updateCourse getting updated course object as {} ", AppUtils.convertObjectToJson(courseResponseDTO));
            log.info(("CourseService method::updateCourse method execution ended .."));
            // Course existingCourse = findByCourseId(courseId);
            //courses.set(courses.indexOf(existingCourse),course);
            //return course;
            return courseResponseDTO;
        } catch (Exception e) {
            log.error("CourseService::onboardNewCourse exception occurs while updating the course object."+ e.getMessage());
            throw new CourseServiceBusinessException("updateCourse service method failed.."+ e.getMessage());
        }
    }
}
