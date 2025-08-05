package com.profect.delivery.domain.users.repository;


import com.profect.delivery.global.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByUserId(String UserId);
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
}
