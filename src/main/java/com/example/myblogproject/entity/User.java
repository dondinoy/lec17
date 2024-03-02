package com.example.myblogproject.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String email;
    @NotNull
    @Pattern
            (regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[*!@$%^&]).{8,32}$"
            ,message = "password must contain at least 1 lowercase letter,1 uppercase letter,1 digit and 1 special character")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            )},
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )

    )
    private Set<Role>roles;

    //one user to many comment
    @OneToMany(mappedBy = "user")
//    @JoinTable(
//            name = "user_comments",
//            joinColumns = {@JoinColumn(
//                    name = "user_id",
//                    referencedColumnName = "id"
//            )
//            },
//            inverseJoinColumns = @JoinColumn(
//                    name = "comment_id",
//                    referencedColumnName = "id"
//            )
//    )
    private Set<Comment>comments= Set.of();
}