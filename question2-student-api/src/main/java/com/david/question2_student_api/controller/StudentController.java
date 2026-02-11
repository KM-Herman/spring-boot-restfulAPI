package com.david.question2_student_api.controller;

import com.david.question2_student_api.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    public StudentController() {
        students.add(new Student(1L, "Alice", "Smith", "alice@student.com", "Computer Science", 3.8));
        students.add(new Student(2L, "Bob", "Jones", "bob@student.com", "Mathematics", 3.2));
        students.add(new Student(3L, "Charlie", "Brown", "charlie@student.com", "Computer Science", 3.5));
        students.add(new Student(4L, "David", "Williams", "david@student.com", "Physics", 2.9));
        students.add(new Student(5L, "Eva", "Miller", "eva@student.com", "Engineering", 3.9));
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        Optional<Student> student = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();
        return student.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/major/{major}")
    public List<Student> getStudentsByMajor(@PathVariable String major) {
        return students.stream()
                .filter(s -> s.getMajor().equalsIgnoreCase(major))
                .collect(Collectors.toList());
    }

    @GetMapping("/filter")
    public List<Student> filterStudentsByGpa(@RequestParam Double gpa) {
        return students.stream()
                .filter(s -> s.getGpa() >= gpa)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student) {
        long nextId = students.stream().mapToLong(Student::getStudentId).max().orElse(0) + 1;
        student.setStudentId(nextId);
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student studentDetails) {
        Optional<Student> studentOptional = students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst();

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            student.setMajor(studentDetails.getMajor());
            student.setGpa(studentDetails.getGpa());
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
