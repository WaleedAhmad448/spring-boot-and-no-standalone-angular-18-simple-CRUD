package com.students.students.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.students.students.genderEnum.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	
	@Column(length = 30,nullable = false)
	private String studentName;
	
	@Column(nullable = false)
	private String studentNrc;
	
	@NotNull
	@Min(value = 1,message = "age should be greater than 1")
	private int age;
	
	@Column(nullable = false)
	private LocalDate dateOfBirth;
    
    @Column(length = 30,nullable = false)
    private String fatherName;
    
    @Column(length = 30,nullable = false)
    @Enumerated(EnumType.STRING)

    private GenderEnum gender;
	
	@Column(length = 10,nullable = false)
    private String township;
	
	@Column(length = 50,nullable = false)
	private String address;
	
	@Column(nullable = false)
	private LocalDate date;
	
	private String photo;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	@OneToMany(mappedBy = "student",cascade = CascadeType.ALL,
	fetch = FetchType.EAGER,orphanRemoval = true)
	@JsonBackReference
	private List<Marks> marks;
	

	/**
	 * @return the mark
	 */
	public List<Marks> getMark() {
		return marks;
	}

	/**
	 * @param mark the mark to set
	 */
	public void setMark(List<Marks> marks) {
		this.marks = marks;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the studentId
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the studentNrc
	 */
	public String getStudentNrc() {
		return studentNrc;
	}

	/**
	 * @param studentNrc the studentNrc to set
	 */
	public void setStudentNrc(String studentNrc) {
		this.studentNrc = studentNrc;
	}

	/**
	 * @return the dateOfBirth
	 */
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the fatherName
	 */
	public String getFatherName() {
		return fatherName;
	}

	/**
	 * @param fatherName the fatherName to set
	 */
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	/**
	 * @return the township
	 */
	public String getTownship() {
		return township;
	}

	/**
	 * @param township the township to set
	 */
	public void setTownship(String township) {
		this.township = township;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/**
	 * @return the gender
	 */
	public GenderEnum getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(GenderEnum gender) {
		this.gender = gender;
	}
	
    /**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}
	 public void addMark(Marks mark) {
	        marks.add(mark);
	        mark.setStudent(this);
	    }

	    public void removeMark(Marks mark) {
	        marks.remove(mark);
	        mark.setStudent(null);
	    }
}
