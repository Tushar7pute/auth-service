package com.projects.auth.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.User;

import javax.persistence.*;

@Entity(name="users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    UserEntity(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
    }

    @Getter
    @NoArgsConstructor
    public static class Builder {

        private long id;
        private String email;
        private String username;
        private String password;




        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserEntity build(){
            return new UserEntity(this);
        }

    }

}
