package com.geekster.DOCTORAPP.service;

import com.geekster.DOCTORAPP.model.Appointment;
import com.geekster.DOCTORAPP.model.Patient;
import com.geekster.DOCTORAPP.repository.IAppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo appointmentRepo;

    public List<Appointment> getAllAppointment() {
        return appointmentRepo.findAll();


    }

    public String addAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
        return "Appointment fixed";
    }
    public Appointment getAppointmentForPatient(Patient patient) {
        return appointmentRepo.findFirstByPatient(patient);
    }

    public void cancelAppointment(Appointment appointment) {

        appointmentRepo.delete(appointment);
    }

}
