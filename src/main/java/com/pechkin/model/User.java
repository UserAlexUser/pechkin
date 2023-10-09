package com.pechkin.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usr")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String secondname;

}
