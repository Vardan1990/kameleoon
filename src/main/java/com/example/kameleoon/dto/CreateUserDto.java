package com.example.kameleoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {

    @NotBlank
    private String userName;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateUserDto)) return false;

        CreateUserDto that = (CreateUserDto) o;

        return new EqualsBuilder().append(userName, that.userName).append(email, that.email).append(password, that.password).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userName).append(email).append(password).toHashCode();
    }

    @Override
    public String toString() {
        return "CreateUserDto{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
