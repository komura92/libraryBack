package com.example.library.Entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false, unique = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    //@Fetch(FetchMode.SUBSELECT)
    //@Singular
    private List<UserRole> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    //@LazyCollection(LazyCollectionOption.FALSE)
    private List<Book> books;
}
