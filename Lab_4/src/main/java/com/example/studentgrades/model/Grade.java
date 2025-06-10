package com.example.studentgrades.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private int score;
    private LocalDate submissionDate;

    @ManyToOne
    @JoinColumn(name = "student_id") // Зовнішній ключ у таблиці grade
    private Student student;

    public Grade() {}

    public Grade(String subject, int score, LocalDate submissionDate) {
        this.subject = subject;
        this.score = score;
        this.submissionDate = submissionDate;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}