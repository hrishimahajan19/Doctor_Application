package com.geekster.DOCTORAPP.controller;

import com.geekster.DOCTORAPP.model.Appointment;
import com.geekster.DOCTORAPP.model.Patient;
import com.geekster.DOCTORAPP.model.dto.SignInInput;
import com.geekster.DOCTORAPP.model.dto.SignUpOutput;
import com.geekster.DOCTORAPP.service.AuthenticationService;
import com.geekster.DOCTORAPP.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("patient/signup")
    public SignUpOutput signUpPatient(@RequestBody Patient patient) {
        return patientService.signUpPatient(patient);
    }

    @PostMapping("patient/signIn")
    public String signInPatient(@RequestBody @Valid SignInInput signInInput) {
        return patientService.signInPatient(signInInput);
    }

    @GetMapping("patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping("appointment")
    public String addAppointment(@RequestBody Appointment appointment, String email, String token) {
        if (authenticationService.authenticate(email, token)) {
            patientService.addAppointment(appointment);
            return "appointment scheduled";
        } else {
            return "Scheduling failed because of authentication";
        }
    }
    @DeleteMapping("patient/signOut")
    public String sigOutPatient(String email, String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return patientService.sigOutPatient(email);
        }
        else {
            return "Sign out not allowed for non authenticated user.";
        }

    }
    @DeleteMapping("appointment/cancel")
    public String  cancelAppointment(String email, String token)
    {

        if(authenticationService.authenticate(email,token)) {
            patientService.cancelAppointment(email);
            return "canceled Appointment successfully";
        }
        else
        {
            return "Scheduling failed because invalid authentication";
        }
    }

}

