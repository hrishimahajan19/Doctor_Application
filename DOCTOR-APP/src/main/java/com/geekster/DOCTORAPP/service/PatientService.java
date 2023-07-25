package com.geekster.DOCTORAPP.service;

import com.geekster.DOCTORAPP.model.Appointment;
import com.geekster.DOCTORAPP.model.AuthenticationToken;
import com.geekster.DOCTORAPP.model.Patient;
import com.geekster.DOCTORAPP.model.dto.SignInInput;
import com.geekster.DOCTORAPP.model.dto.SignUpOutput;
import com.geekster.DOCTORAPP.repository.IAppointmentRepo;
import com.geekster.DOCTORAPP.repository.IAuthTokenRepo;
import com.geekster.DOCTORAPP.repository.IPatientRepo;
import com.geekster.DOCTORAPP.service.emailUtility.EmailHandler;
import com.geekster.DOCTORAPP.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    IAuthTokenRepo authTokenRepo;

    @Autowired

    IAppointmentRepo appointmentRepo;

    @Autowired
    AppointmentService appointmentService;




    public SignUpOutput signUpPatient(Patient patient) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;


        String newEmail = patient.getPatientEmail();

        if (newEmail == null) {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);


        }
        //check if this email already exist ??
        Patient existingPatient = patientRepo.findFirstByPatientEmail(newEmail);
        if (existingPatient != null) {
            signUpStatusMessage = "Email Already Registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(patient.getPatientPassword());

            //save the patient with new encrypted password
            patient.setPatientPassword(encryptedPassword);
            patientRepo.save(patient);
            //signUpStatusMessage = "Email Registered Successfully!!! ";


            return new SignUpOutput(signUpStatus, "Email Registered Successfully !!!");
        } catch (Exception e) {
            signUpStatusMessage = "Internal Error Occured During SignUp!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);


        }


    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();

    }

    public String signInPatient(SignInInput signInInput) {


        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if (signInEmail == null) {


            signInStatusMessage = "Invalid email";

            return signInStatusMessage;
        }
        //check if this email already exist ??
        Patient existingPatient = patientRepo.findFirstByPatientEmail(signInEmail);

        if (existingPatient == null) {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;


        }
        //match passwords
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if (existingPatient.getPatientPassword().equals(encryptedPassword)) {
                //session should be created since password matched and user is valid
                AuthenticationToken authToken = new AuthenticationToken(existingPatient);
                authTokenRepo.save(authToken);
                EmailHandler.sendEmail("hrishimahajan19@gmail.com", "email testing", authToken.getTokenValue());
                return "Token sent to your Email";

            } else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        } catch (Exception e) {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }
    public  String  addAppointment(Appointment appointment) {
        appointmentRepo.save(appointment);
        return "Appointmnet Fixed";
    }
    public String sigOutPatient(String email) {

        Patient patient = patientRepo.findFirstByPatientEmail(email);
        authTokenRepo.delete(authTokenRepo.findFirstByPatient(patient));
        return "Patient Signed out successfully";
    }

    public void cancelAppointment(String email) {

        //email -> Patient
        Patient patient = patientRepo.findFirstByPatientEmail(email);

        Appointment appointment = appointmentService.getAppointmentForPatient(patient);

        appointmentService.cancelAppointment(appointment);
    }


}
