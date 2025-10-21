package com.miniproject;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordEntity {
    @Id
    @SequenceGenerator(name="password_seq",sequenceName = "password_seq",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "password_seq")
    private Long id;
    private String siteUsername;
    private String email;
    private String phoneNumber;
    private String password;
    private String siteUrl;
    private LocalDate createdAt;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
