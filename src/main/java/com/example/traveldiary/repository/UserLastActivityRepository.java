package com.example.traveldiary.repository;

import com.example.traveldiary.model.UserLastActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserLastActivityRepository extends JpaRepository<UserLastActivity, Long> {

    Optional<UserLastActivity> findByUserId(Long id);

}
