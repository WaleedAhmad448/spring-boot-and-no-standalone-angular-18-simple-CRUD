/**
 * 
 */
package com.students.students.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Marks  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int markId;
	
	private LocalDate date;

	private int mark1;
	
	private int mark2;
	
	private int mark3;
	
	private int total;
	
	
	@ManyToOne
	@JoinColumn(name = "student_id",referencedColumnName = "studentId")
	@JsonBackReference
	private Student student;
	
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
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	@PrePersist
	@PreUpdate
	public void caculated() {
		this.total = this.mark1 + this.mark2 + this.mark3;
	}

	/**
	 * @return the markId
	 */
	public int getMarkId() {
		return markId;
	}

	/**
	 * @param markId the markId to set
	 */
	public void setMarkId(int markId) {
		this.markId = markId;
	}

	/**
	 * @return the mark1
	 */
	public int getMark1() {
		return mark1;
	}

	/**
	 * @param mark1 the mark1 to set
	 */
	public void setMark1(int mark1) {
		this.mark1 = mark1;
		this.caculated();
	}

	/**
	 * @return the mark2
	 */
	public int getMark2() {
		return mark2;
	}

	/**
	 * @param mark2 the mark2 to set
	 */
	public void setMark2(int mark2) {
		this.mark2 = mark2;
		this.caculated();
	}

	/**
	 * @return the mark3
	 */
	public int getMark3() {
		return mark3;
	}

	/**
	 * @param mark3 the mark3 to set
	 */
	public void setMark3(int mark3) {
		this.mark3 = mark3;
		this.caculated();
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
		this.caculated();
	}
    

}
