package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import com.example.demo.dto.BorrowRequest;
import com.example.demo.dto.ReturnRequest;

import java.util.List;

@RestController

@RequestMapping("/books")

public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping

    public Book addBook(@RequestBody Book book){

        return bookService.addBook(book);

    }

    @GetMapping

    public List<Book> getBooks(){

        return bookService.getAllBooks();

    }

    @PostMapping("/borrow")

    public Book borrowBook(
            @RequestBody BorrowRequest request
    ){

        return bookService.borrowBook(

                request.getBookId(),
                request.getUserId()

        );

    }

    @PostMapping("/return")

    public Book returnBook(

            @RequestBody ReturnRequest request

    ){

        return bookService.returnBook(

                request.getBookId(),
                request.getUserId()

        );

    }

    // UPDATED METHOD
    @GetMapping("/user/{userId}")

    public List<Book> userBooks(

            @PathVariable String userId

    ){

        return bookService.getUserBooks(userId);

    }

}