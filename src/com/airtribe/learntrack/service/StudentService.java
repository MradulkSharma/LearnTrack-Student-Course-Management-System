package com.airtribe.learntrack.service;

import java.util.ArrayList;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;

public class StudentService {
    private final StudentRepository studentRepository = new StudentRepository();

    public Student addStudent(String firstName, String lastName, String email, String batch) {
        String id = IdGenerator.getNextStudentId();
        Student student = new Student(id, firstName, lastName, email, batch);
        studentRepository.addStudent(student);
        return student;
    }

    public ArrayList<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    public Student findStudentById(String studentId) throws EntityNotFoundException {
        for (Student student : studentRepository.getAllStudents()) {
            if (student.getId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        throw new EntityNotFoundException("Student with ID " + studentId + " was not found.");
    }

    public void deactivateStudent(String studentId) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        student.setActive(false);
    }

    public void updateStudent(String studentId, String newEmail, String newBatch) throws EntityNotFoundException {
        Student student = findStudentById(studentId);
        student.setEmail(newEmail);
        student.setBatch(newBatch);
    }
}
