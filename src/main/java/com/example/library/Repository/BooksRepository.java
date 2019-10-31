package com.example.library.Repository;

import com.example.library.Entity.Book;
import com.example.library.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByUser(User user);
    List<Book> findBooksByRentedAndAuthorAndTitle(boolean rented, String author, String title);
    List<Book> findBooksByImgLinkAndAuthorAndTitle(String imgLink, String author, String title);
    List<Book> findBooksByRentedAndAuthor(boolean rented, String author);
    List<Book> findBooksByRentedAndTitle(boolean rented, String title);
    List<Book> findBooksByRented(boolean rented);

    List<Book> findBooksByAuthorContainingAndTitleContainingAndRented(String[] author, String title, boolean b);

    @Query(nativeQuery =true,value = "select * from books where :condition")
    List<Book> mySearcher(@PathVariable String condition);
}
