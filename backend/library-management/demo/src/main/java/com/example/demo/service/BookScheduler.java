package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;

@Service

public class BookScheduler {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    // Runs every 60 seconds
    @Scheduled(fixedRate = 60000)

    public void checkExpiryBooks(){

        long currentTime = System.currentTimeMillis();

        List<Book> books =
        bookRepository.findByAvailableFalse();

        for(Book book:books){

            if(book.getExpiryTime()!=null){

                if(currentTime > book.getExpiryTime()){

                    autoReturn(book);

                }

            }

        }

    }

    // Library policy books return at 10PM
    @Scheduled(fixedRate = 60000)

    public void checkPolicyBooks(){

        LocalTime now = LocalTime.now();

        if(now.getHour()==22){

            List<Book> books=
            bookRepository.findByPolicy("LIBRARY_ONLY");

            for(Book book:books){

                if(Boolean.FALSE.equals(book.getAvailable())){

                    autoReturn(book);

                }

            }

        }

    }

    // Common return logic

    public void autoReturn(Book book){

        String userId=book.getBorrowedBy();

        if(userId!=null){

            User user=
            userRepository.findById(userId)
            .orElse(null);

            if(user!=null){

                user.getBorrowedBooks()
                .remove(book.getId());

                userRepository.save(user);

            }

        }

        book.setAvailable(true);

        book.setBorrowedBy(null);

        bookRepository.save(book);

    }

}