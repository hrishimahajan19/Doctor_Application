package com.geekster.DOCTORAPP.service;

import com.geekster.DOCTORAPP.model.Doctor;
import com.geekster.DOCTORAPP.repository.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    IDoctorRepo doctorRepo;
    public String addDoctor(Doctor doctor) {
        doctorRepo.save(doctor);
        return "Doctor Added Succesfully";

    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }
}
