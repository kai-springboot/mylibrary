package com.mylibrary.springbootlibrary.controller;

import com.mylibrary.springbootlibrary.entity.Book;
import com.mylibrary.springbootlibrary.responsemodels.ShelfCurrentLoansResponse;
import com.mylibrary.springbootlibrary.service.BookService;
import com.mylibrary.springbootlibrary.utils.ExtractJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mylibrary.springbootlibrary.utils.ExtractJWT.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    public static Logger logger = LoggerFactory.getLogger(BookController.class);
    @PutMapping("/secure/checkout")
    public Book checkoutBook (@RequestHeader(value = "Authorization") String token,@RequestParam Long bookId) throws Exception {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.checkoutBook(userEmail, bookId);
    }

    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutBookByUser(@RequestHeader(value = "Authorization") String token,
                                      @RequestParam Long bookId) {

        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");

        return bookService.checkoutBookByUser(userEmail, bookId);
    }
    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoansCount(userEmail);
    }

    @GetMapping("/secure/currentloans")
    public List<ShelfCurrentLoansResponse> currentLoans(@RequestHeader(value = "Authorization") String token)
            throws Exception
    {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return bookService.currentLoans(userEmail);
    }

    @PutMapping("/secure/return")
    public void returnBook(@RequestHeader(value = "Authorization") String token,
                           @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.returnBook(userEmail, bookId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token,
                          @RequestParam Long bookId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        bookService.renewLoan(userEmail, bookId);
    }
}
