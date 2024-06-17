package com.students.students.serviceImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.students.students.entity.Marks;
import com.students.students.entity.Student;
import com.students.students.genderEnum.GenderEnum;
import com.students.students.repository.MarkRepository;
import com.students.students.repository.StudentRepository;
import com.students.students.service.StudentService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	MarkRepository markRepository;

	@Override
	public Student createStudent(Student student) {
		student.getMark().forEach(mark -> mark.setStudent(student));
		return studentRepository.save(student);
	}

	@Override
	public List<Student> getAllStudent() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudent(int id) {
		Student student = studentRepository.findByStudentId(id);
		student.getMark().forEach(mark->mark.getStudent());
		return student;
	}
	
	

	@Override
	public Student updateStudent(int id, Student student) {
		Student originalStudent = this.getStudent(id);
	     if(originalStudent == null) {
	    	 
	    	 return null;
	     }
//	     originalStudent.setStudentId(student.getStudentId());
	     originalStudent.setStudentName(student.getStudentName());
	     originalStudent.setStudentNrc(student.getStudentNrc());
	     originalStudent.setAge(student.getAge());
	     originalStudent.setDateOfBirth(student.getDateOfBirth());
	     originalStudent.setFatherName(student.getFatherName());
	     originalStudent.setGender(student.getGender());
	     originalStudent.setTownship(student.getTownship());
	     originalStudent.setAddress(student.getAddress());
	     originalStudent.setDate(student.getDate());
	     originalStudent.setPhoto(student.getPhoto());
	     
	     originalStudent.getMark().clear();
	     
	     List<Marks> newMarks = student.getMark();
	     if(newMarks != null) {
	    	 for(Marks newMark : newMarks) {
	    		 newMark.setStudent(originalStudent);
	    		 originalStudent.getMark().add(newMark);
	    	 }
	     }
	     
		return studentRepository.save(originalStudent);
	}

	@Override
	public boolean deleteStudent(int id) {
		Student student = this.getStudent(id);
		if(student == null) {
			return false;
		}
		
		studentRepository.delete(student);
		
		return true;
	}

	@Override
	public Student findByStudentName(String studentName) {
		return studentRepository.findByStudentName(studentName);
	}

	@Override
	public List<Student> getAllByStudentNameLikeStudentName(String studentName) {
		return studentRepository.findByStudentNameStartingWithIgnoreCase(studentName);
	}

	@Override
	public byte[] exportToExcel() throws IOException {
		 List<Student> students = studentRepository.findAll();

	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Students");

	        // Create header row
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("Date");
	        header.createCell(1).setCellValue("ID");
	        header.createCell(2).setCellValue("Photo");
	        header.createCell(3).setCellValue("Name");
	        header.createCell(4).setCellValue("Nrc NO");
	        header.createCell(5).setCellValue("Age");
	        header.createCell(6).setCellValue("BirthDay");
	        header.createCell(7).setCellValue("Father Name");
	        header.createCell(8).setCellValue("Gender");
	        header.createCell(9).setCellValue("Address");
	        header.createCell(10).setCellValue("Township");
	        header.createCell(11).setCellValue("Mark Date");
	        header.createCell(12).setCellValue("Mark 1");
	        header.createCell(13).setCellValue("Mark 2");
	        header.createCell(14).setCellValue("Mark 3");
	        header.createCell(15).setCellValue("Total");
	        

	        // Populate data rows
	        int rowNum = 1;
	        for (Student student : students) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(student.getDate().
	            		format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	            row.createCell(1).setCellValue(student.getStudentId());
	            
	            row.createCell(2).setCellValue(student.getPhoto());
	            
	            row.createCell(3).setCellValue(student.getStudentName());
	            row.createCell(4).setCellValue(student.getStudentNrc());
	            row.createCell(5).setCellValue(student.getAge());
	            row.createCell(6).setCellValue(student.getDateOfBirth().
	            		format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	            row.createCell(7).setCellValue(student.getFatherName());
	            row.createCell(8).setCellValue(student.getGender().toString());
	            row.createCell(9).setCellValue(student.getAddress());
	            row.createCell(10).setCellValue(student.getTownship());
	            
	            List<Marks> marksList = student.getMark();
	            for (int i = 0; i < marksList.size(); i++) {
	                Marks mark = marksList.get(i);
	                row.createCell(11 + i).setCellValue(mark.getDate());
	                row.createCell(12 + i).setCellValue(mark.getMark1());
	                row.createCell(13 + i).setCellValue(mark.getMark2());
	                row.createCell(14 + i).setCellValue(mark.getMark3());
	                row.createCell(15 + i).setCellValue(mark.getTotal());
	            }
	        }

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        workbook.close();
	        
	        return outputStream.toByteArray();
	    }

	@Override
	@Transactional
	public void importFromExcel(MultipartFile file) {
		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			Integer lastId = studentRepository.findMaxStudentId();
			int newId = (lastId != null) ? lastId + 1 : 1;

			Integer lastMarkId = markRepository.findMaxMarkId();
			int newMarkId = (lastMarkId != null) ? lastMarkId + 1 : 1;

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row != null) {
					Student student = new Student();

					student.setDate(parseLocalDate(getStringCellValue(row.getCell(0)), dateFormatter));
					student.setStudentId(newId++);

					student.setStudentName(getStringCellValue(row.getCell(2)));
					student.setStudentNrc(getStringCellValue(row.getCell(3)));
					student.setAge((int) row.getCell(4).getNumericCellValue());
					student.setDateOfBirth(parseLocalDate(getStringCellValue(row.getCell(5)), dateFormatter));
					student.setGender(GenderEnum.valueOf(getStringCellValue(row.getCell(6))));
					student.setFatherName(getStringCellValue(row.getCell(7)));
					student.setTownship(getStringCellValue(row.getCell(8)));
					student.setAddress(getStringCellValue(row.getCell(9)));
					student.setPhoto(getStringCellValue(row.getCell(10)));

					List<Marks> marksList = new ArrayList<>();
					for (int j = 11; j < row.getLastCellNum(); j += 5) {
						if (j + 4 < row.getLastCellNum()) {
							LocalDate markDate = parseLocalDate(getStringCellValue(row.getCell(j)), dateFormatter);
							Marks mark = new Marks();
							mark.setMarkId(newMarkId++);
							mark.setDate(markDate);
							mark.setMark1((int) row.getCell(j + 1).getNumericCellValue());
							mark.setMark2((int) row.getCell(j + 2).getNumericCellValue());
							mark.setMark3((int) row.getCell(j + 3).getNumericCellValue());
							mark.setTotal((int) row.getCell(j + 4).getNumericCellValue());

							marksList.add(mark);
						}
					}
					student.setMark(marksList);

					student.getMark().forEach(mark -> mark.setStudent(student));

					studentRepository.save(student);
				}
			}
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error occurred during the import process: " + e.getMessage());
		}
	}

	private String getStringCellValue(Cell cell) {
		if (cell == null) {
			return null;
		}
		if (cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getLocalDateTimeCellValue().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			} else {
				return String.valueOf((int) cell.getNumericCellValue());
			}
		}
		return null;
	}

	private LocalDate parseLocalDate(String value, DateTimeFormatter formatter) {
		if (value == null) {
			return null;
		}
		return LocalDate.parse(value, formatter);
	}

}


