package com.gadgetnest.app.repository;

import com.gadgetnest.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
