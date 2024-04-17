package com.thanhtd.glassstore.dao;

import com.thanhtd.glassstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.status <> 0")
    List<User> findAllUsers();

    @Query("SELECT u FROM User u WHERE u.status <> 0 AND u.userId = :userId")
    User findByUserId(@Param("userId") Long userId);

    @Query("SELECT u FROM User u WHERE u.status <> 0 AND u.email = :email")
    User findByEmail(@Param("email") String email);
}
