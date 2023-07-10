package com.projects.auth.services;

import com.projects.auth.db.entities.UserEntity;
import com.projects.auth.db.repos.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;
    PasswordService passwordService;

    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public UserEntity createUser(String email, String userName , String password) {

        UserEntity userEntity = new UserEntity.Builder()
                .setEmail(email)
                .setUsername(userName)
                .setPassword(passwordService.createHash(password))
                .build();
        return userRepository.save(userEntity);
    }

    public UserEntity verifyUser(String username, String password) {
        UserEntity savedUser = userRepository.findByUsername(username);

        if (savedUser == null) {
            throw new UserNotFoundException(username);
        }

        if(!passwordService.matchPassword(password, savedUser.getPassword())) {
            throw  new UserPasswordMismatchException(username);
        }
        return savedUser;
    }
    public UserEntity getUserByUsername(String username) {
        UserEntity savedUser = userRepository.findByUsername(username);

        if (savedUser == null) {
            throw new UserNotFoundException(username);
        }

        /* creating and returning a UserEntity without the password field -
        In order to restrict password propagation above this layer.
         */

        return new UserEntity.Builder()
                .setId(savedUser.getId())
                .setUsername(savedUser.getUsername())
                .setEmail(savedUser.getEmail())
                .build();
    }


    static class UserNotFoundException extends IllegalStateException {
        public UserNotFoundException(String username) {
            super("User not found for the username = " + username);
        }
    }

    static class UserPasswordMismatchException extends IllegalArgumentException {
        public UserPasswordMismatchException(String username) {
            super("Wrong password provided for the username = " + username);
        }
    }

}
