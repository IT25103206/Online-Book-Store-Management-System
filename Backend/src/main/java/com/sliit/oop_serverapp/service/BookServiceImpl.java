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

    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setDescription(book.getDescription());
        dto.setPrice(book.getPrice());
        dto.setQuantity(book.getQuantity());
        // OOP Concept: Polymorphism
        // The DTO is populated based on the specific type of Book entity (EBook or PrintedBook)
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getName());
        }
        if (book.getCategory() != null) {
            dto.setCategoryId(book.getCategory().getId());
        }
        dto.setImagePath(book.getImagePath());
        dto.setIsBestseller(book.getIsBestseller());
        
        if (book instanceof EBook) {
            dto.setBookType("EBOOK");
            dto.setDownloadUrl(((EBook) book).getDownloadUrl());
            dto.setFileSizeMb(((EBook) book).getFileSizeMb());
        } else if (book instanceof PrintedBook) {
            dto.setBookType("PRINTED");
            dto.setWeight(((PrintedBook) book).getWeight());
            dto.setDimensions(((PrintedBook) book).getDimensions());
        } else {
            dto.setBookType("BOOK");
        }
        return dto;
    }

    private void updateEntityFromDTO(Book book, BookDTO dto) {
        book.setName(dto.getName());
        book.setDescription(dto.getDescription());
        book.setPrice(dto.getPrice());
        book.setQuantity(dto.getQuantity());
        book.setImagePath(dto.getImagePath());
        book.setIsBestseller(dto.getIsBestseller() != null ? dto.getIsBestseller() : false);
        
        if (dto.getAuthorId() != null) {
            Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));
            book.setAuthor(author);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + dto.getCategoryId()));
            book.setCategory(category);
        }

        if (book instanceof EBook && dto.getDownloadUrl() != null) {
            ((EBook) book).setDownloadUrl(dto.getDownloadUrl());
            ((EBook) book).setFileSizeMb(dto.getFileSizeMb());
        } else if (book instanceof PrintedBook && dto.getWeight() != null) {
            ((PrintedBook) book).setWeight(dto.getWeight());
            ((PrintedBook) book).setDimensions(dto.getDimensions());
        }
    }

    
}
