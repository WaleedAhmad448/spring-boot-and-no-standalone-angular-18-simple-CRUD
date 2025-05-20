package com.students.students.serviceImpl;

import com.students.students.entity.Teacher;
import com.students.students.repository.TeacherRepository;
import com.students.students.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    private static final String UPLOAD_DIR = "uploads/teachers/";

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher not found"));
    }

    @Override
    public List<Teacher> searchByName(String name) {
        return teacherRepository.findByFullNameContainingIgnoreCase(name);
    }

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher updateTeacher(Long id, Teacher updated) {
        Teacher teacher = getTeacherById(id);
        teacher.setFullName(updated.getFullName());
        teacher.setEmail(updated.getEmail());
        teacher.setSubject(updated.getSubject());
        return teacherRepository.save(teacher);
    }

    @Override
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public String uploadPhoto(Long id, MultipartFile file) {
        Teacher teacher = getTeacherById(id);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fileName);

        try {
            dest.getParentFile().mkdirs(); // إنشاء المجلد إذا لم يكن موجودًا
            file.transferTo(dest);
            teacher.setPhotoPath(dest.getPath());
            teacherRepository.save(teacher);
            return "تم رفع الصورة بنجاح";
        } catch (IOException e) {
            throw new RuntimeException("فشل في رفع الصورة", e);
        }
    }
}
