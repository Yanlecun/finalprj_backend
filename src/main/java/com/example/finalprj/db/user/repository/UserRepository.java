package com.example.finalprj.db.user.repository;

import com.example.finalprj.db.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
