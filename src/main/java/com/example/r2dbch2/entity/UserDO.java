package com.example.r2dbch2.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Collection;

@Table("APP_USER")
@Data
@NoArgsConstructor
public class UserDO {
    @Id
    private Long id;

    private String email;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String password;

    private Collection<String> roles;

    public UserDO(String email, String firstName, String lastName, String password, Collection<String> roles) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
    }
}
