package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.AuthorDTO;
import com.sliit.oop_serverapp.entity.Author;
import com.sliit.oop_serverapp.entity.GuestAuthor;
import com.sliit.oop_serverapp.exception.ResourceNotFoundException;
import com.sliit.oop_serverapp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OOP Concept: Abstraction & Polymorphism
 * AuthorServiceImpl implements AuthorService, providing concrete behavior 
 * for managing authors while abstracting the data access layer.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author;
        if ("GUEST".equalsIgnoreCase(authorDTO.getAuthorType())) {
            author = new GuestAuthor();
        } else {
            author = new Author();
        }
        updateEntityFromDTO(author, authorDTO);
        return convertToDTO(authorRepository.save(author));
    }

    @Override
    public AuthorDTO updateAuthor(AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorDTO.getId()));
        updateEntityFromDTO(author, authorDTO);
        return convertToDTO(authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found with id: " + id);
        }
        authorRepository.deleteById(id);
    }

    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setDiscription(author.getDiscription());
        
        if (author instanceof GuestAuthor) {
            dto.setAuthorType("GUEST");
            dto.setInstitution(((GuestAuthor) author).getInstitution());
            dto.setCountry(((GuestAuthor) author).getCountry());
        } else {
            dto.setAuthorType("AUTHOR");
        }
        return dto;
    }

    private void updateEntityFromDTO(Author author, AuthorDTO dto) {
        author.setName(dto.getName());
        author.setDiscription(dto.getDiscription());
        
        if (author instanceof GuestAuthor) {
            ((GuestAuthor) author).setInstitution(dto.getInstitution());
            ((GuestAuthor) author).setCountry(dto.getCountry());
        }
    }
}
