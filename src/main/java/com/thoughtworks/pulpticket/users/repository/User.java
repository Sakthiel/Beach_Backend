package com.thoughtworks.pulpticket.users.repository;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "usertable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(name = "user_name" , nullable = false , unique = true)
    @Pattern(regexp = ("[a-zA-Z$_][a-zA-Z0-9$_]*"), message = "Please enter valid username")
    private String username;
    @Column(nullable = false)
    @Pattern(regexp = ("^.*[a-zA-Z0-9]+.*$"), message = "Please enter valid password")
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
