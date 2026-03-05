package com.mountainjourney.mjauth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Integer id;

    @Column(name = "User_FirstName")
    private String firstName;

    @Column(name = "User_LastName")
    private String lastName;

    @Column(name = "User_Phone")
    private String phone;

    @Column(name = "User_Email", nullable = false, unique = true)
    private String email;

    @Column(name = "User_Password", nullable = false)
    private String password;
}