package com.gadgetnest.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is Required")
    private String name;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is Required")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password is Required")
    private String password;

    @NotEmpty(message = "Phone is Required")
    private String phone;

    @NotEmpty(message = "Answer is Required")
    private String answer;

    @NotEmpty(message = "Address is Required")
    private String address;

    @Builder.Default
    private int role = 0;

}
