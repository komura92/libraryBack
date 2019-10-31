package com.example.library.Entity.Model.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookModel {
    private Long id;
    private String author;
    private String description;
    private String title;
    private String imgLink;
}
