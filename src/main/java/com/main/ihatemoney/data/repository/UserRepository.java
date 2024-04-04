package com.main.ihatemoney.data.repository;

import com.main.ihatemoney.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select count(u.id) from users u where u.email = :email")
    int findUserCountByEmail(@Param("email") String email);

    @Query("select DISTINCT u from users u where u.email = :email")
    User findUserByEmail(@Param("email") String email);

    @Query("select DISTINCT u from users u where u.id = :userId")
    User findUserById(Long userId);
}
