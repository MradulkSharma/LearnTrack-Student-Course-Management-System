# LearnTrack Student and Course Management System

LearnTrack is a small console application for managing students, courses, and enrollments. It uses Core Java and keeps all information in memory while the program is running.

## Features

- Add, view, search, and deactivate students
- Add and view courses, and change their active status
- Enroll students in courses
- View a student's enrollments
- Mark an enrollment completed or cancelled
- Handle invalid menu choices and missing IDs with clear messages

## Project structure

```text
src/com/airtribe/learntrack/
  Main.java                 Menu and application entry point
  entity/                   Person, Student, Course, Enrollment
  repository/               In-memory ArrayList storage
  service/                  StudentService, CourseService, EnrollmentService
  exception/                EntityNotFoundException
  constants/                AppConstants, MenuOptions
  enums/                    EnrollmentStatus
  util/                     IdGenerator
docs/                       Setup and short design notes
```

The repositories store data in `ArrayList` objects. The data is cleared when the program exits.

## Run from a terminal

Install a JDK, then open PowerShell in this project folder and run:

```powershell
javac -d out src/com/airtribe/learntrack/Main.java src/com/airtribe/learntrack/entity/*.java src/com/airtribe/learntrack/repository/*.java src/com/airtribe/learntrack/service/*.java src/com/airtribe/learntrack/exception/*.java src/com/airtribe/learntrack/constants/*.java src/com/airtribe/learntrack/enums/*.java src/com/airtribe/learntrack/util/*.java
java -cp out com.airtribe.learntrack.Main
```

## Class diagram

```text
Person
  |
  +-- Student

Main
  +-- StudentService --> StudentRepository --> Students
  +-- CourseService --> CourseRepository --> Courses
  +-- EnrollmentService --> EnrollmentRepository --> Enrollments
        +-- uses StudentService and CourseService

Enrollment refers to a Student and a Course by ID.
```

See `docs/Setup_Instructions.md`, `docs/JVM_Basics.md`, and `docs/Design_Notes.md` for short learning notes.
