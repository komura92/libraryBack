package com.example.library.Service;

import com.example.library.Assembler.BookAssembler;
import com.example.library.Entity.Book;
import com.example.library.Entity.Model.Book.BookModel;
import com.example.library.Exceptions.BookNotFoundException;
import com.example.library.Exceptions.BookNotRentedForUserException;
import com.example.library.Exceptions.BookRentedException;
import com.example.library.Login.MyUserDetails;
import com.example.library.Repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Component
@RequiredArgsConstructor
public class BooksService {

    private final BookAssembler bookAssembler;
    private final BooksRepository booksRepository;

    public List<BookModel> getAllBooks() {
        return bookAssembler.entityListToModelList(booksRepository.findBooksByRented(false));
    }

    public Book getBookById(Long id) {
        return booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<BookModel> getMyBooks() {
        MyUserDetails user = MyUserDetails.getActualUser();
        return bookAssembler.entityListToModelList(booksRepository.findBooksByUser(user));
    }

    public List<Book> getBooksByAuthor(String author) {
        return booksRepository.findBooksByRentedAndAuthor(false, author);
    }

    public List<Book> getBooksByTitle(String title) {
        return booksRepository.findBooksByRentedAndTitle(false, title);
    }

    public List<Book> getBooksByAuthorAndTitle(String author, String title) {
        return booksRepository.findBooksByRentedAndAuthorAndTitle(false, author, title);
    }

    @Transactional
    public boolean addBook(Book book) {
        booksRepository.save(book);
        return true;
    }

    @Transactional
    public boolean editBook(Book book) {
        Book book1 = booksRepository.findById(book.getId()).orElseThrow(() -> new BookNotFoundException(book.getId()));
        book1.setAuthor(book.getAuthor());
        book1.setTitle(book.getTitle());
        book1.setUser(book.getUser());
        book1.setRented(book.getRented());

        //booksRepository.save(book);

        return true;
    }

    public void deleteBook(Long id) {
        Book book = booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        booksRepository.delete(book);
    }

    public List<Book> getBooksBy(String author, String title) {
        //TODO better search (split by space, lower case works fine)
        return booksRepository.mySearcher("where title like '%hArry%' or title like '%poter%'");
    }

    @Transactional
    public void rent(Long id) {
        Book book = booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        if (book.getRented())
            throw new BookRentedException(id);

        MyUserDetails user = MyUserDetails.getActualUser();
        user.getBooks().add(book);
        book.setRented(true);
        book.setUser(user);
    }

    @Transactional
    public void returnBook(Long id) {
        Book book = booksRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        MyUserDetails user = MyUserDetails.getActualUser();

        //if book is yours
        if ((!book.getRented()) || (!(book.getUser().getId() == user.getId())))
            throw new BookNotRentedForUserException(id);

        user.getBooks().remove(book);
        book.setRented(false);
        book.setUser(null);
    }
}
