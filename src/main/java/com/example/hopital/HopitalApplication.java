package com.example.hopital;

import com.example.hopital.entities.Patient;
import com.example.hopital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {
@Autowired
private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(new Patient(null,"smaille",new Date(),false,33));
        patientRepository.save(new Patient(null,"saade",new Date(),true,3223));
        patientRepository.save(new Patient(null,"naime",new Date(),false,323));
    }
}
