package com.example.l;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;


@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book takeBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && !book.getTaken()) {
            book.setTaken(true);
            bookRepository.save(book);
            return book;
        } else {
            throw new IllegalArgumentException("Book not available for borrowing");
        }
    }

    public Book returnBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getTaken()) {
            book.setTaken(false);
            bookRepository.save(book);
            return book;
        } else {
            throw new IllegalArgumentException("Book cannot be returned. Invalid operation.");
        }
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public void removeBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }


    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
