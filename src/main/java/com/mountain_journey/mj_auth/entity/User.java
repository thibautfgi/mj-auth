package com.mountain_journey.mj_auth.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Integer userId;

    @Column(name = "User_FirstName")
    private String userFirstName;

    @Column(name = "User_LastName")
    private String userLastName;

    @Column(name = "User_Phone")
    private String userPhone;

    @Column(name = "User_Email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "User_Password", nullable = false)
    private String userPassword;
}