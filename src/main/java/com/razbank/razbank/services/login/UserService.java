package com.razbank.razbank.services.login;

import com.razbank.razbank.dtos.customer.CustomerDTO;
import com.razbank.razbank.entities.login.Role;
import com.razbank.razbank.entities.login.User;
import com.razbank.razbank.repositories.login.RoleRepository;
import com.razbank.razbank.repositories.login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByName(userName);
    }

    public User saveUser(User user, CustomerDTO customerDTO) {
        return userRepository.save(buildUser(user,customerDTO));
    }

    private User buildUser(User user, CustomerDTO customerDTO) {
        user.setCustomerId(customerDTO.getId());
        user.setName(customerDTO.getName());
        user.setLastName(customerDTO.getLastName());
        user.setEmail(customerDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return user;
    }

}
