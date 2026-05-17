package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.BookDTO;
import com.sliit.oop_serverapp.entity.Book;
import com.sliit.oop_serverapp.entity.EBook;
import com.sliit.oop_serverapp.entity.PrintedBook;
import com.sliit.oop_serverapp.entity.Author;
import com.sliit.oop_serverapp.entity.Category;
import com.sliit.oop_serverapp.exception.ResourceNotFoundException;
import com.sliit.oop_serverapp.repository.AuthorRepository;
import com.sliit.oop_serverapp.repository.BookRepository;
import com.sliit.oop_serverapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OOP Concept: Abstraction & Implementation
 * BookServiceImpl implements the BookService interface, hiding the complex business logic 
 * from the controller and providing a clear implementation of required methods.
 */
@Service
public class BookServiceImpl implements BookService {

    // OOP Concept: Dependency Injection (a form of Inversion of Control)
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book;
        if ("EBOOK".equalsIgnoreCase(bookDTO.getBookType())) {
            book = new EBook();
        } else if ("PRINTED".equalsIgnoreCase(bookDTO.getBookType())) {
            book = new PrintedBook();
        } else {
            book = new Book();
        }
        updateEntityFromDTO(book, bookDTO);
        return convertToDTO(bookRepository.save(book));
    }

    @Override
    public BookDTO updateBook(BookDTO bookDTO) {
        Book book = bookRepository.findById(bookDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookDTO.getId()));
        updateEntityFromDTO(book, bookDTO);
        return convertToDTO(bookRepository.save(book));
    }

    @Override
    public void deleteBook(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    
}
