package com.example.myblogproject.config;

import com.example.myblogproject.entity.Role;
import com.example.myblogproject.entity.User;
import com.example.myblogproject.repository.RoleRepository;
import com.example.myblogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class SQLRunner implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
//    @Transactional

    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            var adminRole = roleRepository.save(new Role(1L, "ROLE_ADMIN"));
            var userRole = roleRepository.save(new Role(2L, "ROLE_USER"));

            userRepository.save(
                    new User(
                            1L,
                            "admin",
                            "sss@gmail.com",
                            passwordEncoder.encode("passw0rd1!P"),
                            Set.of(adminRole),Set.of()
                    )
            );
            userRepository.save(
                    new User(
                            2L,
                            "user",
                            "aaa@gmail.com",
                            passwordEncoder.encode("passw0rd1!P"),
                            Set.of(userRole),Set.of()
                    )
            );
        }
    }
}