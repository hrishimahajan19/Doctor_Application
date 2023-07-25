package com.geekster.DOCTORAPP.controller;

import com.geekster.DOCTORAPP.model.Doctor;
import com.geekster.DOCTORAPP.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DoctorController {

    @Autowired
    DoctorService doctorService;
    @PostMapping("doctor")
    public String addDoctor(@RequestBody Doctor doctor){
        return doctorService.addDoctor(doctor);
    }

    @GetMapping("doctors")
    public List<Doctor> getAllDoctors(){
        return doctorService.getAllDoctors();
    }
}
