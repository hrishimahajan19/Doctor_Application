package com.geekster.DOCTORAPP.repository;

import com.geekster.DOCTORAPP.model.Appointment;
import com.geekster.DOCTORAPP.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAppointmentRepo extends JpaRepository<Appointment,Long> {

    Appointment findFirstByPatient(Patient patient);
}
