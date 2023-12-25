package com.metrodata.serverapp.entity;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@Table(name = "invitation")
@AllArgsConstructor
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Person participant;

    @ManyToOne
    private Meeting meeting;

    @ManyToOne
    private Status status;

    @OneToMany(mappedBy = "invitation")
    private List<ConfirmationToken> confirmationToken;
}