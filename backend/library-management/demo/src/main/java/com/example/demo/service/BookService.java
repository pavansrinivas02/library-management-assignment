package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;

@Service

public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    public Book addBook(Book book){

        if(book.getAvailable()==null)
            book.setAvailable(true);

        return bookRepository.save(book);

    }

    public List<Book> getAllBooks(){

        return bookRepository.findAll();

    }

    public Book borrowBook(String bookId,String userId){

        Book book=
        bookRepository.findById(bookId)
        .orElseThrow(() -> new RuntimeException("Book not found"));

        if(Boolean.FALSE.equals(book.getAvailable())){

            throw new RuntimeException("Book already borrowed");

        }

        User user=
        userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

        book.setAvailable(false);

        book.setBorrowedBy(userId);

        bookRepository.save(book);

        user.getBorrowedBooks().add(bookId);

        userRepository.save(user);

        return book;

    }

    public Book returnBook(String bookId,String userId){

        Book book=
        bookRepository.findById(bookId)
        .orElseThrow(() -> new RuntimeException("Book not found"));

        User user=
        userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

        book.setAvailable(true);

        book.setBorrowedBy(null);

        bookRepository.save(book);

        user.getBorrowedBooks().remove(bookId);

        userRepository.save(user);

        return book;

    }

    // UPDATED METHOD (returns full book details)
    public List<Book> getUserBooks(String userId){

        User user=
        userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

        return bookRepository.findAllById(user.getBorrowedBooks());

    }

}