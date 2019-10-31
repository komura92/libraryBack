package com.example.library.Repository;

import com.example.library.Entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRole, Long> {

}
