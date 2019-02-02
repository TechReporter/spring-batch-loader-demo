/**
 * 
 */
package com.pluralsight.springbatch.patientbatchloader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pluralsight.springbatch.patientbatchloader.domain.PatientEntity;

/**
 * @author Tanmoy Dasgupta
 */
@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

}
