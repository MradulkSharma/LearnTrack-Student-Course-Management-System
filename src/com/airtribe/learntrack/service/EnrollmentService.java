package com.airtribe.learntrack.service;

import java.time.LocalDate;
import java.util.ArrayList;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(String studentId, String courseId) throws EntityNotFoundException {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new IllegalArgumentException("Student is inactive and cannot be enrolled.");
        }

        if (!course.isActive()) {
            throw new IllegalArgumentException("Course is inactive and cannot accept enrollments.");
        }

        String id = IdGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(id, student.getId(), course.getId(), LocalDate.now().toString(), EnrollmentStatus.ACTIVE);
        enrollmentRepository.addEnrollment(enrollment);
        return enrollment;
    }

    public ArrayList<Enrollment> getEnrollmentsForStudent(String studentId) throws EntityNotFoundException {
        studentService.findStudentById(studentId);

        ArrayList<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollmentRepository.getAllEnrollments()) {
            if (enrollment.getStudentId().equalsIgnoreCase(studentId)) {
                result.add(enrollment);
            }
        }

        return result;
    }

    public void markEnrollmentCompleted(String studentId, String courseId) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollment(studentId, courseId);
        enrollment.setStatus(EnrollmentStatus.COMPLETED);
    }

    public void cancelEnrollment(String studentId, String courseId) throws EntityNotFoundException {
        Enrollment enrollment = findEnrollment(studentId, courseId);
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
    }

    private Enrollment findEnrollment(String studentId, String courseId) throws EntityNotFoundException {
        for (Enrollment enrollment : enrollmentRepository.getAllEnrollments()) {
            if (enrollment.getStudentId().equalsIgnoreCase(studentId)
                    && enrollment.getCourseId().equalsIgnoreCase(courseId)) {
                return enrollment;
            }
        }

        throw new EntityNotFoundException("Enrollment for student " + studentId + " in course " + courseId + " does not exist.");
    }
}
