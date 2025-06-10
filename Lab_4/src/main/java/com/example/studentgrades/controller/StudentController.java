package com.example.studentgrades.controller;

import com.example.studentgrades.model.Grade;
import com.example.studentgrades.model.Student;
import com.example.studentgrades.repository.GradeRepository;
import com.example.studentgrades.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        model.addAttribute("hasStudents", !students.isEmpty());
        return "index";
    }

    @PostMapping("/")
    public String addStudent(@RequestParam("studentName") String studentName,
                             @RequestParam("patronymic") String patronymic,
                             @RequestParam("subject") String subject,
                             @RequestParam("score") int score,
                             @RequestParam("submissionDate") String submissionDateStr) {
        Student student = studentRepository.findByName(studentName)
                .orElse(new Student(studentName, patronymic));
        LocalDate submissionDate = LocalDate.parse(submissionDateStr);
        Grade grade = new Grade(subject, score, submissionDate);
        grade.setStudent(student); // Встановлюємо зворотний зв’язок
        student.addGrade(grade);
        studentRepository.save(student);
        return "redirect:/";
    }

    @PostMapping("/deleteGrade")
    public String deleteGrade(@RequestParam("gradeId") Long gradeId) {
        // Знаходимо оцінку
        Grade grade = gradeRepository.findById(gradeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid grade Id: " + gradeId));
        // Видаляємо оцінку з колекції студента
        Student student = grade.getStudent();
        student.getGrades().remove(grade);
        studentRepository.save(student); // orphanRemoval видалить запис із таблиці grade
        return "redirect:/";
    }

    @GetMapping("/editGrade/{id}")
    public String editGradeForm(@PathVariable("id") Long id, Model model) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid grade Id: " + id));
        model.addAttribute("grade", grade);
        return "editGrade";
    }

    @PostMapping("/updateGrade")
    public String updateGrade(@RequestParam("id") Long id,
                              @RequestParam("subject") String subject,
                              @RequestParam("score") int score,
                              @RequestParam("submissionDate") String submissionDateStr) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid grade Id: " + id));
        grade.setSubject(subject);
        grade.setScore(score);
        grade.setSubmissionDate(LocalDate.parse(submissionDateStr));
        gradeRepository.save(grade);
        return "redirect:/";
    }
}