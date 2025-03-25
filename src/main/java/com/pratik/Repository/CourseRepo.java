package com.pratik.Repository;

import com.pratik.Entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends CrudRepository<CourseEntity, Integer> {


}
