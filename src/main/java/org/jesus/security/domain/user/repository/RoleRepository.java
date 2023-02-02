package org.jesus.security.domain.user.repository;

import org.jesus.security.domain.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
