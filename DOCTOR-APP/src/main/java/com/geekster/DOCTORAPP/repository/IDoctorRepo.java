package com.geekster.DOCTORAPP.repository;

import com.geekster.DOCTORAPP.model.Doctor;
import com.geekster.DOCTORAPP.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDoctorRepo extends JpaRepository<Doctor,Long> {
}
