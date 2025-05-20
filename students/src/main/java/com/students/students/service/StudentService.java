/**
 * 
 */
package com.students.students.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.students.students.entity.Student;

public interface StudentService {
	
	Student createStudent(Student student);
	
	List<Student> getAllStudent();
	
	Student getStudent(int id);
	
	Student updateStudent(int id,Student student);
	
	boolean deleteStudent(int id);
	
	public Student findByStudentName(String studentName);
	
	public List<Student> getAllByStudentNameLikeStudentName(String studentName);
	
	public byte[] exportToExcel() throws IOException;
	
    void importFromExcel(MultipartFile file) throws IOException;
	
}
