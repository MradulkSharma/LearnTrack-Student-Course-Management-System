package com.airtribe.learntrack;

import java.util.ArrayList;
import java.util.Scanner;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final StudentService studentService = new StudentService();
    private final CourseService courseService = new CourseService();
    private final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        while (true) {
            printMenu();
            String input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input.trim());
                if (choice == MenuOptions.EXIT) {
                    System.out.println("Goodbye!");
                    break;
                }
                handleMenuChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("============================");
        System.out.println("     " + AppConstants.APP_NAME);
        System.out.println("============================");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Search Student");
        System.out.println("4. Deactivate Student");
        System.out.println("5. Add Course");
        System.out.println("6. View Courses");
        System.out.println("7. Activate/Deactivate Course");
        System.out.println("8. Enroll Student");
        System.out.println("9. View Enrollments");
        System.out.println("10. Mark Completed");
        System.out.println("11. Cancel Enrollment");
        System.out.println("12. Exit");
        System.out.println("============================");
        System.out.print("Enter choice: ");
    }

    private void handleMenuChoice(int choice) {
        try {
            switch (choice) {
                case MenuOptions.ADD_STUDENT:
                    addStudent();
                    break;
                case MenuOptions.VIEW_STUDENTS:
                    viewStudents();
                    break;
                case MenuOptions.SEARCH_STUDENT:
                    searchStudentById();
                    break;
                case MenuOptions.DEACTIVATE_STUDENT:
                    deactivateStudent();
                    break;
                case MenuOptions.ADD_COURSE:
                    addCourse();
                    break;
                case MenuOptions.VIEW_COURSES:
                    viewCourses();
                    break;
                case MenuOptions.CHANGE_COURSE_STATUS:
                    toggleCourseStatus();
                    break;
                case MenuOptions.ENROLL_STUDENT:
                    enrollStudent();
                    break;
                case MenuOptions.VIEW_ENROLLMENTS:
                    viewEnrollmentsForStudent();
                    break;
                case MenuOptions.COMPLETE_ENROLLMENT:
                    markEnrollmentCompleted();
                    break;
                case MenuOptions.CANCEL_ENROLLMENT:
                    cancelEnrollment();
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addStudent() {
        System.out.print("First name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Batch: ");
        String batch = scanner.nextLine();

        Student student = studentService.addStudent(firstName, lastName, email, batch);
        System.out.println("Student added. ID: " + student.getId());
    }

    private void viewStudents() {
        ArrayList<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Student student : students) {
            System.out.println("ID: " + student.getId() + " | Name: " + student.getDisplayName()
                    + " | Email: " + student.getEmail() + " | Batch: " + student.getBatch()
                    + " | Active: " + student.isActive());
        }
    }

    private void searchStudentById() throws EntityNotFoundException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        Student student = studentService.findStudentById(studentId);
        System.out.println("Student found: " + student.getDisplayName());
    }

    private void deactivateStudent() throws EntityNotFoundException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        studentService.deactivateStudent(studentId);
        System.out.println("Student deactivated.");
    }

    private void addCourse() {
        System.out.print("Course name: ");
        String courseName = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Duration in weeks: ");
        int duration;
        try {
            duration = Integer.parseInt(scanner.nextLine());
            if (duration < 1) {
                System.out.println("Duration must be at least 1 week.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter the duration as a number.");
            return;
        }

        Course course = courseService.addCourse(courseName, description, duration);
        System.out.println("Course added. ID: " + course.getId());
    }

    private void viewCourses() {
        ArrayList<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        for (Course course : courses) {
            System.out.println("ID: " + course.getId() + " | Name: " + course.getCourseName()
                    + " | Weeks: " + course.getDurationInWeeks() + " | Active: " + course.isActive());
        }
    }

    private void toggleCourseStatus() throws EntityNotFoundException {
        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        Course course = courseService.findCourseById(courseId);

        if (course.isActive()) {
            courseService.deactivateCourse(courseId);
            System.out.println("Course deactivated.");
        } else {
            courseService.activateCourse(courseId);
            System.out.println("Course activated.");
        }
    }

    private void enrollStudent() throws EntityNotFoundException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrollment created. ID: " + enrollment.getId());
    }

    private void viewEnrollmentsForStudent() throws EntityNotFoundException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        ArrayList<Enrollment> enrollments = enrollmentService.getEnrollmentsForStudent(studentId);

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }

        for (Enrollment enrollment : enrollments) {
            System.out.println("Enrollment ID: " + enrollment.getId() + " | Course: " + enrollment.getCourseId()
                    + " | Date: " + enrollment.getEnrollmentDate() + " | Status: " + enrollment.getStatus());
        }
    }

    private void markEnrollmentCompleted() throws EntityNotFoundException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        enrollmentService.markEnrollmentCompleted(studentId, courseId);
        System.out.println("Enrollment marked completed.");
    }

    private void cancelEnrollment() throws EntityNotFoundException {
        System.out.print("Enter student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter course ID: ");
        String courseId = scanner.nextLine();

        enrollmentService.cancelEnrollment(studentId, courseId);
        System.out.println("Enrollment cancelled.");
    }
}


