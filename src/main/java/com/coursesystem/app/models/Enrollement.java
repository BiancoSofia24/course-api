package com.coursesystem.app.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coursesystem.app.enums.EEnrollmentType;
import com.coursesystem.app.enums.EStatus;

@Entity
@Table(name = "enrollments")
public class Enrollement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courses_id")
    private Course course;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "students_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    private EStatus enrollmentStatus;

    @Enumerated(EnumType.STRING)
    private EEnrollmentType type;

    public Enrollement() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return this.course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return this.student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public EStatus getEnrollmentStatus() {
        return this.enrollmentStatus;
    }

    public void setEnrollmentStatus(EStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public EEnrollmentType getType() {
        return this.type;
    }

    public void setType(EEnrollmentType type) {
        this.type = type;
    }

}
