package com.students.students.repository;

import com.students.students.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByFullNameContainingIgnoreCase(String fullName);
}
