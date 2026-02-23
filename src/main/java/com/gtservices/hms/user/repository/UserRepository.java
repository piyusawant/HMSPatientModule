package com.gtservices.hms.user.repository;

import com.gtservices.hms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String emailId);

    boolean existsByMobileNo(String contactNo);
}
