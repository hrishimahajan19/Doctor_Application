package com.geekster.DOCTORAPP.repository;

import com.geekster.DOCTORAPP.model.AuthenticationToken;
import com.geekster.DOCTORAPP.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Long> {
   public  AuthenticationToken findFirstByTokenValue(String authTokenValue);


   AuthenticationToken findFirstByPatient(Patient patient);
}
