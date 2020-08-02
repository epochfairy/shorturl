package com.example.demo.service;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public DataFetcher<CompletableFuture<Book>> getBook(){
        return environment -> {
            String bookId =environment.getArgument("id");
            return bookRepository.getBook(bookId).toFuture();

        };

    }

    public DataFetcher<CompletableFuture<List<Book>>> getBooks() {
        return environment -> bookRepository.getBooks().collectList().toFuture();
    }

    public DataFetcher<CompletableFuture<String>> createBook(){
        return environment -> {
            String name = environment.getArgument("name");
            int pages = environment.getArgument("pages");
            return bookRepository.createBook(new Book(name,pages)).map(Objects::toString).toFuture();
        };
    }
}
