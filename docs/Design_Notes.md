# Design Notes

## Why ArrayList is used
ArrayList is better than a normal array because the size can grow while the program is running. In this project, we do not know how many students or courses will be added.

## Why static members are used
Static fields are used in the ID generator so each student, course, and enrollment gets a unique ID automatically.

## Why inheritance is used
The `Student` class extends `Person`. This helps us reuse common properties like name and email instead of writing them again.

## Why repositories are used
Each repository keeps one `ArrayList` for its type of data. The matching service uses that repository when it adds or reads objects.

## Why constants and an enum are used
`MenuOptions` gives names to the menu numbers, and `AppConstants` stores the menu title. `EnrollmentStatus` limits an enrollment to `ACTIVE`, `COMPLETED`, or `CANCELLED`.

