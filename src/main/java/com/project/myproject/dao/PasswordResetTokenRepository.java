package com.project.myproject.dao;

import com.project.myproject.entity.PasswordResetToken;
import com.project.myproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    Stream<PasswordResetToken> findAllByExpiryDateLessThan(LocalDateTime now);

    void deleteByExpiryDateLessThan(LocalDateTime now);

    @Query("delete from PasswordResetToken t where t.expiryDate <= :now")
    void deleteAllExpiredSince(LocalDateTime now);
}
