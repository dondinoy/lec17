package com.example.myblogproject.service;

import com.example.myblogproject.dto.UserRequestDto;
import com.example.myblogproject.dto.UserResponseDto;
import com.example.myblogproject.error.UserAlreadyExistsException;
import com.example.myblogproject.repository.RoleRepository;
import com.example.myblogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    //props:
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //fetch our entity from database:
        var user = userRepository.findUserByUsernameIgnoreCase(username).orElseThrow(
                () -> new UsernameNotFoundException(username)
        );
        //map the roles for Spring:
        //map our Set<Role> to Set<spring.Role>
        var roles =
                user.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toSet());
        //map the user to Spring User:
        //return new Spring User:
        return new User(user.getUsername(), user.getPassword(), roles);
    }
    @Override
    public UserResponseDto register(UserRequestDto dto) {
        //check that the user does not exist email/username:
        userRepository.findUserByUsernameIgnoreCaseOrEmailIgnoreCase(dto.username(), dto.email()).ifPresent((u) -> {
            throw new UserAlreadyExistsException(u.getUsername(), u.getEmail());
        });


        var user = modelMapper.map(dto, com.example.myblogproject.entity.User.class);

        //encrypt password
        user.setPassword(passwordEncoder.encode(dto.password()));
        var role = roleRepository.findRoleByNameIgnoreCase("ROLE_USER").orElseThrow();
        user.setRoles(Set.of(role));
        var saved = userRepository.save(user);

        return modelMapper.map(saved, UserResponseDto.class);

    }


}
