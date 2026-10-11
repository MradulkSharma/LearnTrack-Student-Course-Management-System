package com.airtribe.learntrack.util;

public class IdGenerator {
    private static int studentIdCounter = 1;
    private static int courseIdCounter = 1;
    private static int enrollmentIdCounter = 1;

    public static String getNextStudentId() {
        return "STU-" + studentIdCounter++;
    }

    public static String getNextCourseId() {
        return "COURSE-" + courseIdCounter++;
    }

    public static String getNextEnrollmentId() {
        return "ENR-" + enrollmentIdCounter++;
    }
}
