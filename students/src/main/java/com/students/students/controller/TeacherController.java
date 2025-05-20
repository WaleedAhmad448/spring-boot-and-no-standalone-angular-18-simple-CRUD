package com.students.students.controller;

import com.students.students.entity.Teacher;
import com.students.students.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/get/all")
    public List<Teacher> getAll() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/get/{id}")
    public Teacher getById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/name")
    public List<Teacher> searchByName(@RequestParam String fullName) {
        return teacherService.searchByName(fullName);
    }

    @PostMapping("/create")
    public Teacher create(@RequestBody Teacher teacher) {
        return teacherService.createTeacher(teacher);
    }

    @PutMapping("/update/{id}")
    public Teacher update(@PathVariable Long id, @RequestBody Teacher teacher) {
        return teacherService.updateTeacher(id, teacher);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }

    @PostMapping("/photo/upload/{id}")
    public String uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return teacherService.uploadPhoto(id, file);
    }
}
