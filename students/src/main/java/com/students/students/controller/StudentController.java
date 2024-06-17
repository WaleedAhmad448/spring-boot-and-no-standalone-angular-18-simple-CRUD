/**
 * 
 */
package com.students.students.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.students.students.entity.Student;
import com.students.students.service.StorageService;
import com.students.students.service.StudentService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	StorageService storageService;
	
	@Autowired
	StudentService studentService;
	
	@PostMapping(value = "/create")
	public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student){
		return ResponseEntity.ok().body(studentService.createStudent(student));
	}
	
	@GetMapping(value = "/get/all")
	public ResponseEntity<List<Student>> getAllStudent(){
		return ResponseEntity.ok().body(studentService.getAllStudent());
	}
	
	@GetMapping(value = "/get/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable int id){
		return ResponseEntity.ok().body(studentService.getStudent(id));
	}
	
	@GetMapping("/name")
	public ResponseEntity<List<Student>> getAllStudentByNameLikeStudent(@RequestParam String studentName){
		List<Student> students = studentService.getAllByStudentNameLikeStudentName(studentName);
		return ResponseEntity.ok().body(students);
	}
	
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable int id,
			@Valid @RequestBody Student student){
		Student updateStudent = studentService.updateStudent(id, student);
		if(updateStudent == null) {
			return null;
		}
		return ResponseEntity.ok().body(updateStudent);
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> deleteStudent(@PathVariable int id){
		Student student = studentService.getStudent(id);
		
		if(student == null) {
			return ResponseEntity.notFound().build();
		}
		
		boolean isDelete = studentService.deleteStudent(id);
		
		if(!isDelete) {
			return ResponseEntity.notFound().build();
		}
		
		storageService.delete(student.getPhoto());
		
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType) {
		String filePath = storageService.create(file, fileType);
		if (filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	}

	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType, @RequestParam("filePath") String filePath) {
		String newFilePath = storageService.update(file, fileType, filePath);
		if (newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}
	
	@PostMapping("/import")
	public ResponseEntity<String> importStudents(@RequestParam("file") MultipartFile file) {
	    try {
	        studentService.importFromExcel(file);
	        return ResponseEntity.ok().body("File imported successfully.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import file: " + e.getMessage());
	    }
	}

	 @GetMapping("/export")
	    public ResponseEntity<byte[]> exportStudents() {
	        try {
	            byte[] excelData = studentService.exportToExcel();

	            return ResponseEntity.ok()
	                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
	                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
	                    .body(excelData);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    }
	
	}
