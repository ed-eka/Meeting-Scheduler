package com.metrodata.serverapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "confirmation_token")
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String token;

    @Column(name = "create_date")
    private LocalDateTime createdDate;

    @Column(name = "confirm_date")
    private LocalDateTime confirmDate;

    @ManyToOne
    private Invitation invitation;
}
