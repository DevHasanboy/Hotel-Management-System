package com.example.file.task.repository;


import com.example.file.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);


    @Query(value = "select * from users where role='ADMIN'", nativeQuery = true)
    List<User> findAllAdmin();
}
