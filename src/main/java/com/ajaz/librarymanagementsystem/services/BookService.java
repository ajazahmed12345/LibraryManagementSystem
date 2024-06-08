package com.ajaz.librarymanagementsystem.services;

import com.ajaz.librarymanagementsystem.exceptions.BookNotFoundException;
import com.ajaz.librarymanagementsystem.exceptions.UserNotFoundException;
import com.ajaz.librarymanagementsystem.models.Book;
import com.ajaz.librarymanagementsystem.models.BookStatus;
import com.ajaz.librarymanagementsystem.models.User;
import com.ajaz.librarymanagementsystem.repositories.BookRepository;
import com.ajaz.librarymanagementsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    public Book createBook(Book book){

        return bookRepository.save(book);
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(bookOptional.isEmpty())
            throw new BookNotFoundException("Book with id = " + id + " does not exist.");

        return bookOptional.get();
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public String deleteBookById(Long id) throws BookNotFoundException {
        Book book = getBookById(id);

        bookRepository.deleteById(id);

        return "Book with id = " + id + " has been removed.";

    }

    public String borrowBook(Long userId, Long bookId) throws UserNotFoundException, BookNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(userOptional.isEmpty())
            throw new UserNotFoundException("User trying to book is not present.");

        if(bookOptional.isEmpty())
            throw new BookNotFoundException("Book to be booked does not exist.");

        User user = userOptional.get();
        Book book = bookOptional.get();

        if(book.getBookStatus().equals(BookStatus.BOOKED))
            throw new BookNotFoundException("Book is already mapped with this User");

        book.setBorrowedBy(user);
        book.setBookStatus(BookStatus.BOOKED);

        bookRepository.save(book);

        return "Book with title = " + book.getTitle() + " has been mapped with the user " + user.getName();
    }

    public String returnBook(Long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if(bookOptional.isEmpty())
            throw new BookNotFoundException("Book trying to return with id = " + bookId + " " +
                    "does not exist");

        Book book = bookOptional.get();

        if(book.getBookStatus().equals(BookStatus.NOT_BOOKED))
            throw new BookNotFoundException("This book is not already returned to the Library");

        String userName = book.getBorrowedBy().getName();

        book.setBorrowedBy(null);
        book.setBookStatus(BookStatus.NOT_BOOKED);

        bookRepository.save(book);

        return "Book " + book.getTitle() + " borrowed by User " + userName + " returned to the Library";
    }
}
