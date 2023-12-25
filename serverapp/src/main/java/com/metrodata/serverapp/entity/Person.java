package com.metrodata.serverapp.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String address;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_employee")
    private boolean isEmployee = true;

    @Column(name = "is_visible")
    private boolean isVisible = true;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Account account;

    @OneToMany(mappedBy = "participant")
    private List<Invitation> invitations;

    @OneToMany(mappedBy = "initiator")
    private List<Meeting> meetings;
}