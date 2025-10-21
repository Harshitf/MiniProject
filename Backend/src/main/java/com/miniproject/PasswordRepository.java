package com.miniproject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasswordRepository extends JpaRepository<PasswordEntity,Long> {
PasswordEntity findBySiteUrl(String website);
    long countByUserUsername(String username);

    @Query("SELECT p FROM PasswordEntity p WHERE " +
            "LOWER(p.siteUsername) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.siteUrl) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.notes) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<PasswordEntity> findByKeyword(String keyword);

    void deleteAllByUserUsername(String username);
}
