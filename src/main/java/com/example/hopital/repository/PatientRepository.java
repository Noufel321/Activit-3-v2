package com.example.hopital.repository;

import com.example.hopital.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository  extends JpaRepository<Patient,Long> {
    Page<Patient> findByNomContains(String Keyword ,Pageable pageable);
    @Query("select page from Patient page where  page.nom like :x")
    Page<Patient> chercher(@Param("x") String Keyword , Pageable pageable);
}
