package com.gtservices.hms.config;


import com.gtservices.hms.user.entity.Role;
import com.gtservices.hms.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if PATIENT role exists
        if (roleRepository.findByRoleName("PATIENT").isEmpty()) {
            Role patientRole = new Role();
            patientRole.setRoleName("PATIENT");
            roleRepository.save(patientRole);
        }

        // Optionally, add other roles like DOCTOR, ADMIN
        if (roleRepository.findByRoleName("DOCTOR").isEmpty()) {
            Role doctorRole = new Role();
            doctorRole.setRoleName("DOCTOR");
            roleRepository.save(doctorRole);
        }

        if (roleRepository.findByRoleName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            roleRepository.save(adminRole);
        }

        System.out.println("Default roles initialized");
    }
}
