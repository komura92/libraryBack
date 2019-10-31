package com.example.library.Assembler;

import com.example.library.Entity.Book;
import com.example.library.Entity.Model.Book.BookModel;
import com.example.library.Exceptions.BookNotFoundException;
import com.example.library.Repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class BookAssembler {

    private final BooksRepository booksRepository;
    public BookModel entityToModel(Book book) {
        return  BookModel.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .description(book.getDescription())
                .title(book.getTitle())
                .imgLink(book.getImgLink())
                .build();
    }

    public List<BookModel> entityListToModelList(List<Book> books) {
        List<BookModel> bookModels = new ArrayList<>();

        for (Book book : books) {
            bookModels.add(entityToModel(book));
        }

        return bookModels;
    }

    public Book modelToEntity(BookModel bookModel) {
        return booksRepository.findById(bookModel.getId()).orElseThrow(() -> new BookNotFoundException(bookModel.getId())); //TODO
    }

    public List<Book> modelListToEntityList(List<BookModel> bookModels) {
        List<Book> books = new ArrayList<>();

        for (BookModel bookModel : bookModels) {
            books.add(modelToEntity(bookModel));
        }

        return books;
    }
}
