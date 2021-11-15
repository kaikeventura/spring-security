package com.kaikeventura.spring_security.repository;

import com.kaikeventura.spring_security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
