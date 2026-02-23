package com.gtservices.hms.doctor.serviceImpl;

import com.gtservices.hms.doctor.dto.DoctorRegistrationRequestDto;
import com.gtservices.hms.doctor.dto.DoctorResponseDto;
import com.gtservices.hms.doctor.entity.*;
import com.gtservices.hms.doctor.mapper.DoctorMapper;
import com.gtservices.hms.doctor.repository.DegreeMasterRepository;
import com.gtservices.hms.doctor.repository.DoctorRepository;
import com.gtservices.hms.doctor.repository.SpecializationRepository;
import com.gtservices.hms.doctor.service.DoctorService;
import com.gtservices.hms.user.entity.Role;
import com.gtservices.hms.user.entity.User;
import com.gtservices.hms.user.repository.RoleRepository;
import com.gtservices.hms.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecializationRepository specializationRepository;
    private final DegreeMasterRepository degreeMasterRepository;
    private final DoctorMapper doctorMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public DoctorResponseDto registerDoctor(DoctorRegistrationRequestDto request) {

        // 🔹 Duplicate User Check
        if (userRepository.existsByMobileNo(request.getContactNo())) {
            throw new RuntimeException("Mobile number already registered");
        }

        if (userRepository.existsByEmail(request.getEmailId())) {
            throw new RuntimeException("Email already registered");
        }

        // 🔹Create & Fetch DOCTOR Role
        Role doctorRole = roleRepository
                .findByRoleName("DOCTOR")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setRoleName("DOCTOR");
                    return roleRepository.save(newRole);
                });

        // 🔹 Create User (WITHOUT ENCODING)
        User user = new User();
        user.setFullName(request.getDoctorName());
        user.setMobileNo(request.getContactNo());
        user.setEmail(request.getEmailId());
        user.setPasswordHash("123456"); // Plain password (temporary)
        user.setRole(doctorRole);
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // 🔹 Create Doctor
        Doctor doctor = new Doctor();

        doctor.setDoctorName(request.getDoctorName());
        doctor.setGuardianName(request.getGuardianName());
        doctor.setContactNo(request.getContactNo());
        doctor.setEmailId(request.getEmailId());
        doctor.setAddress(request.getAddress());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setAge(request.getAge());
        doctor.setGender(request.getGender());
        doctor.setConsultationFees(request.getConsultationFees());
        doctor.setHasExperience(request.getHasExperience());
        doctor.setIsActive(true);
        doctor.setDoctorUid("DOC-" + UUID.randomUUID().toString().substring(0, 8));

        // 🔹 Link User to Doctor
        doctor.setUser(savedUser);

        // 🔹 Specialization
        Specialization specialization = specializationRepository
                .findBySpecializationNameIgnoreCase(request.getSpecializationName())
                .orElseGet(() -> {
                    Specialization newSpec = new Specialization();
                    newSpec.setSpecializationName(request.getSpecializationName());
                    newSpec.setIsActive(true);
                    return specializationRepository.save(newSpec);
                });

        doctor.setSpecialization(specialization);

        // 🔹 Education
        List<DoctorEducation> educationList = request.getEducations().stream().map(ed -> {

            DegreeMaster degree = degreeMasterRepository
                    .findByDegreeNameIgnoreCase(ed.getDegreeName())
                    .orElseGet(() -> {
                        DegreeMaster newDegree = new DegreeMaster();
                        newDegree.setDegreeName(ed.getDegreeName());
                        return degreeMasterRepository.save(newDegree);
                    });

            DoctorEducation education = new DoctorEducation();
            education.setDoctor(doctor);
            education.setDegree(degree);
            education.setUniversityName(ed.getUniversityName());
            education.setYearOfPassing(ed.getYearOfPassing());
            return education;

        }).toList();

        doctor.setEducations(educationList);

        // 🔹 Experience
        List<DoctorExperience> experienceList = request.getExperiences().stream().map(exp -> {

            DoctorExperience experience = new DoctorExperience();
            experience.setDoctor(doctor);
            experience.setHospitalName(exp.getHospitalName());
            experience.setHospitalAddress(exp.getHospitalAddress());
            experience.setExperienceYears(exp.getExperienceYears());
            experience.setExperienceMonths(exp.getExperienceMonths());
            return experience;

        }).toList();

        doctor.setExperiences(experienceList);

        // 🔹 Availability
        List<DoctorAvailability> availabilityList = request.getAvailabilities().stream().map(av -> {

            DoctorAvailability availability = new DoctorAvailability();
            availability.setDoctor(doctor);
            availability.setDayOfWeek(av.getDayOfWeek());
            availability.setStartTime(av.getStartTime());
            availability.setEndTime(av.getEndTime());
            return availability;

        }).toList();

        doctor.setAvailabilities(availabilityList);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }
}
