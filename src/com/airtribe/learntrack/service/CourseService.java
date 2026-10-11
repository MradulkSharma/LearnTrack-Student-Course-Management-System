package com.airtribe.learntrack.service;

import java.util.ArrayList;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;

public class CourseService {
    private final CourseRepository courseRepository = new CourseRepository();

    public Course addCourse(String courseName, String description, int duration) {
        String id = IdGenerator.getNextCourseId();
        Course course = new Course(id, courseName, description, duration);
        courseRepository.addCourse(course);
        return course;
    }

    public ArrayList<Course> getAllCourses() {
        return courseRepository.getAllCourses();
    }

    public Course findCourseById(String courseId) throws EntityNotFoundException {
        for (Course course : courseRepository.getAllCourses()) {
            if (course.getId().equalsIgnoreCase(courseId)) {
                return course;
            }
        }

        throw new EntityNotFoundException("Course with ID " + courseId + " was not found.");
    }

    public void deactivateCourse(String courseId) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        course.setActive(false);
    }

    public void activateCourse(String courseId) throws EntityNotFoundException {
        Course course = findCourseById(courseId);
        course.setActive(true);
    }
}
