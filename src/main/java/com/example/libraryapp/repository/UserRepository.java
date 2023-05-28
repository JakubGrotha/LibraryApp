package com.example.libraryapp.repository;

import com.example.libraryapp.model.User;
import com.example.libraryapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(String email);

    @Query("SELECT u FROM User u WHERE u.role = :userRole")
    List<User> findByUserRole(@Param("userRole") UserRole userRole);
}
