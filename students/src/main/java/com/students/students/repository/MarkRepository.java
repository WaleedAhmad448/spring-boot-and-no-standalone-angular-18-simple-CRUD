/**
 * 
 */
package com.students.students.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.students.students.entity.Marks;

@Repository
@EnableJpaRepositories
public interface MarkRepository extends JpaRepository<Marks, Integer>  {

	@Query(value = "SELECT MAX(m.markId) FROM Marks m")
	Integer findMaxMarkId();
}
