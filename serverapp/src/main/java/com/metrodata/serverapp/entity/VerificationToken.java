package com.metrodata.serverapp.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String token;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "expired_date", nullable = false)
    private LocalDateTime expiredDate;
    @Column(name = "confirmed_date")
    private LocalDateTime confirmedDate;

    @ManyToOne
    private Account account;
}