package com.students.students.entity;

import jakarta.persistence.*;

@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String subject;
    private String photoPath; // مسار الصورة على السيرفر أو في النظام

    // === Constructors ===

    public Teacher() {
        // Constructor فارغ
    }

    public Teacher(Long id, String fullName, String email, String subject, String photoPath) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.subject = subject;
        this.photoPath = photoPath;
    }

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
