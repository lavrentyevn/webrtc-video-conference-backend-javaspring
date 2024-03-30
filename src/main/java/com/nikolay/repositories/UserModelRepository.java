package com.nikolay.repositories;

import com.nikolay.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByRefreshToken(String refreshToken);
    Optional<UserModel> findByEmailOrClientUsername(String email, String username);
    List<UserModel> findAllByEmailIn(List<String> email);
}
