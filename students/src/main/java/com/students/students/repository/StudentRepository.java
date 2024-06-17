package com.students.students.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.students.students.entity.Student;

@EnableJpaRepositories
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
	
	public Student findByStudentName(String studentName);
	
	public Student findByStudentId(int id);
	
	@Query("SELECT s FROM Student s " + "WHERE lower(s.studentName) LIKE lower(concat('%', :studentName, '%')) ")
	List<Student> findByStudentNameStartingWithIgnoreCase(@Param("studentName")String studentName);
	
	
	 @org.springframework.data.jpa.repository.Query(value = "SELECT MAX(s.studentId) FROM Student s")
	    Integer findMaxStudentId();
	 
	 @org.springframework.data.jpa.repository.Query("SELECT s FROM Student s LEFT JOIN FETCH s.marks WHERE s.studentId = :id")
	    Optional<Student> findByIdWithMarks(@Param("id") int id); 
}
