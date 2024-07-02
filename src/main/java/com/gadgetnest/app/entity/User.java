package com.gadgetnest.app.entity;

import com.gadgetnest.app.Enum.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
