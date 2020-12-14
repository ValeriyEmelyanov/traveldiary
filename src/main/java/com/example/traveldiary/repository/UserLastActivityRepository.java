package com.example.traveldiary.repository;

import com.example.traveldiary.model.UserLastActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserLastActivityRepository extends JpaRepository<UserLastActivity, Long> {

    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO user_last_activity (user_id, last_activity, description)" +
                    " VALUES (:id, :last_activity, :description)")
    void save(@Param("id") Long id,
              @Param("last_activity") LocalDateTime last_activity,
              @Param("description") String description);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE user_last_activity " +
                    "SET last_activity=:last_activity, description=:description " +
                    "WHERE user_id=:id")
    void update(@Param("id") Long id,
                @Param("last_activity") LocalDateTime last_activity,
                @Param("description") String description);

}
