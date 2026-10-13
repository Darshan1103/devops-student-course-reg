package com.university;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    @Test
    void shouldRegisterStudentForValidCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS301"));
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectInactiveCourse() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                false,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
    }

    @Test
    void shouldRejectMissingPrerequisite() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS401"));
    }

    @Test
    void shouldAllowRegistrationWhenPrerequisiteIsCompleted() {

        Student student = new Student("S101", "Anita");
        student.addCompletedPrerequisite("CS301");

        Course course = new Course(
                "CS401",
                "Advanced Algorithms",
                4,
                30,
                true,
                "CS301"
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertTrue(result);
        assertTrue(student.isRegistered("CS401"));
    }

    @Test
    void shouldRejectDuplicateRegistration() {

        Student student = new Student("S101", "Anita");

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        assertTrue(service.register(student, course));

        boolean secondAttempt = service.register(student, course);

        assertFalse(secondAttempt);
        assertEquals(4, student.getTotalCredits());
        assertEquals(1, course.getCurrentEnrollment());
    }

    @Test
    void shouldRejectRegistrationWhenCreditLimitIsExceeded() {

        Student student = new Student("S101", "Anita");

        // Simulate 20 existing credits
        student.addRegistration("CS101", 4);
        student.addRegistration("CS102", 4);
        student.addRegistration("CS201", 4);
        student.addRegistration("CS202", 4);
        student.addRegistration("CS203", 4);

        Course course = new Course(
                "CS301",
                "Data Structures",
                4,
                30,
                true,
                null
        );

        RegistrationService service = new RegistrationService();

        boolean result = service.register(student, course);

        assertFalse(result);
        assertFalse(student.isRegistered("CS301"));
     }

     @Test
     void shouldRejectRegistrationWhenCourseIsFull() {
    Student firstStudent = new Student("S201", "Rahul");
    Student secondStudent = new Student("S202", "Priya");
    Course course = new Course(
	            "CS501",
	            "Operating Systems",
	            4,
	            1,
	            true,
	            null
	    );

    RegistrationService service = new RegistrationService();

    // The first student fills the only available seat.
    assertTrue(service.register(firstStudent, course));
    assertEquals(1, course.getCurrentEnrollment());

    // The second student must be rejected.
    boolean result = service.register(secondStudent, course);

    assertFalse(result);
    assertFalse(secondStudent.isRegistered("CS501"));
    assertEquals(0, secondStudent.getTotalCredits());
    assertEquals(1, course.getCurrentEnrollment());
    }
    @Test
    void shouldAllowRegistrationWhenExactlyOneSeatRemains() {

       Student firstStudent = new Student("S203", "Arun");
       Student secondStudent = new Student("S204", "Meena");

       Course course = new Course(
            "CS502",
            "Computer Networks",
            3,
            2,
            true,
            null
       );

       RegistrationService service = new RegistrationService();

    // The first student occupies one of two seats.
       assertTrue(service.register(firstStudent, course));
       assertEquals(1, course.getCurrentEnrollment());

    // Exactly one seat remains, so the second registration should succeed.
       boolean result = service.register(secondStudent, course);

       assertTrue(result);
       assertTrue(secondStudent.isRegistered("CS502"));
       assertEquals(3, secondStudent.getTotalCredits());
       assertEquals(2, course.getCurrentEnrollment());
    }
 


}
