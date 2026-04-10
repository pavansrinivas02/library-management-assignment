package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.Book;
import com.example.demo.model.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

import java.time.LocalTime;
import java.util.List;

@Component
public class BookScheduler {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    // Runs every 60 seconds
    @Scheduled(fixedRate = 60000)
    public void checkExpiryBooks(){

        try{

            long currentTime = System.currentTimeMillis();

            List<Book> books =
                    bookRepository.findByAvailableFalse();

            for(Book book : books){

                if(book.getExpiryTime()!=null &&
                   currentTime > book.getExpiryTime()){

                    autoReturn(book);

                }
            }

        }catch(Exception e){

            System.out.println("Scheduler error (expiry): "+e.getMessage());

        }

    }

    // Library policy books return at 10PM
    @Scheduled(fixedRate = 60000)
    public void checkPolicyBooks(){

        try{

            LocalTime now = LocalTime.now();

            if(now.getHour()==22){

                List<Book> books =
                        bookRepository.findByPolicy("LIBRARY_ONLY");

                for(Book book : books){

                    if(Boolean.FALSE.equals(book.getAvailable())){

                        autoReturn(book);

                    }

                }

            }

        }catch(Exception e){

            System.out.println("Scheduler error (policy): "+e.getMessage());

        }

    }

    // Common return logic
    private void autoReturn(Book book){

        try{

            String userId = book.getBorrowedBy();

            if(userId!=null){

                User user =
                        userRepository.findById(userId)
                                .orElse(null);

                if(user!=null &&
                   user.getBorrowedBooks()!=null){

                    user.getBorrowedBooks()
                            .remove(book.getId());

                    userRepository.save(user);

                }

            }

            book.setAvailable(true);
            book.setBorrowedBy(null);

            bookRepository.save(book);

        }catch(Exception e){

            System.out.println("Auto return error: "+e.getMessage());

        }

    }

}