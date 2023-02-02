package org.jesus.security.domain.user.repository;

import org.jesus.security.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.roles where u.userName = :userName and u.isDeleted = false")
    Optional<User> findUserByUserName(@Param(value="userName") String userName);
}
