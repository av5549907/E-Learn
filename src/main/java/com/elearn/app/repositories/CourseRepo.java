package com.elearn.app.repositories;

import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepo extends JpaRepository<Course,String> {
    Optional<Course> findByTitle(String title);
    List<Course> findByLive(Boolean live);
    Course findByCourseId(String courseId);
//    @Query("select c from Course c where c.categoryList" )
//    List<Course> findByCategory(String categoryId);
//    @Query("update c from Course c where courseId='courseId'")
//    Course updateCourse(Course course,String courseId);



}
