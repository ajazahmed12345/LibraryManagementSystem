package com.ajaz.librarymanagementsystem.controllers;

import com.ajaz.librarymanagementsystem.exceptions.BookNotFoundException;
import com.ajaz.librarymanagementsystem.exceptions.UserNotFoundException;
import com.ajaz.librarymanagementsystem.models.Book;
import com.ajaz.librarymanagementsystem.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }
    @CrossOrigin(origins = "*")
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.deleteBookById(id), HttpStatus.OK);
    }

    @GetMapping("/borrow/{userId}/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long userId, @PathVariable Long bookId) throws UserNotFoundException, BookNotFoundException {
        return new ResponseEntity<>(bookService.borrowBook(userId, bookId), HttpStatus.OK);
    }

    @GetMapping("/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable("bookId") Long bookId) throws BookNotFoundException {
        return new ResponseEntity<>(bookService.returnBook(bookId), HttpStatus.OK);
    }
}
