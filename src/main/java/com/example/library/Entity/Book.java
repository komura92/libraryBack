package com.example.library.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String title;

    @Column(nullable = false, unique = false)
    private String author;

    @Column(nullable = false, unique = false)
    private String description;

    @Column(nullable = false, unique = false)
    private String imgLink;

    @Column(nullable = false, unique = false)
    private Boolean rented;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
