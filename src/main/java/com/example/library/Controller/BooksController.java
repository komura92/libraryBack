package com.example.library.Controller;

import com.example.library.Entity.Book;
import com.example.library.Entity.Model.Book.BookModel;
import com.example.library.Service.BooksService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    @Autowired
    private final BooksService booksService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookModel> getAllBooks() {
        return booksService.getAllBooks();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBookById(@PathVariable Long id) {
        return booksService.getBookById(id);
    }

    @RequestMapping(value = "/myBooks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BookModel> getMyBooks() {
        return booksService.getMyBooks();
    }

    @RequestMapping(value = "/getBooksBy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooksBy(String author, String title) {
        return booksService.getBooksBy(author, title);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addBook(@RequestBody Book book) {
        booksService.addBook(book);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteBook(Long id) {
        booksService.deleteBook(id);
    }

    @RequestMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public void editBook(@RequestBody Book book) {
        booksService.editBook(book);
    }

    @RequestMapping(value = "/rent/{id}", method = RequestMethod.GET)
    public void rent(@PathVariable Long id) {
        booksService.rent(id);
    }

    @RequestMapping(value = "/return/{id}", method = RequestMethod.GET)
    public void returnBook(@PathVariable Long id) {
        booksService.returnBook(id);
    }

    @RequestMapping(value = "/search/{author}/{title}", method = RequestMethod.GET)
    public List<Book> searcher(@PathVariable String author, @PathVariable String title) {
        return booksService.getBooksBy(author, title);
    }
}