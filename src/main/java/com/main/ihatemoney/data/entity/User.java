package com.main.ihatemoney.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;

@Entity(name = "users")
public class User extends AbstractEntity implements UserDetails {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Email
    @Column(name = "username")
    private String email;

    private boolean allowMarketingEmails;

    private boolean enabled = true;

    @NotNull
    @Enumerated
    private Role role;

    @Size(min = 8, max = 128, message = "Password must be at least 8 characters, no more than 128 characters.")
    private String password;

    private String resetToken;

    private Timestamp tokenExpiration;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAllowMarketingEmails() {
        return allowMarketingEmails;
    }

    public void setAllowMarketingEmails(boolean allowMarketingEmails) {
        this.allowMarketingEmails = allowMarketingEmails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Timestamp getTokenExpiration() {
        return tokenExpiration;
    }

    public void setTokenExpiration(Timestamp tokenExpiration) {
        this.tokenExpiration = tokenExpiration;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email +  '\'' +
                ", allowMarketingEmails=" + allowMarketingEmails +
                ", enabled=" + enabled +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}
