package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.AuthorDTO;
import java.util.List;

public interface AuthorService {
    List<AuthorDTO> getAllAuthors();
    AuthorDTO createAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthor(AuthorDTO authorDTO);
    void deleteAuthor(Integer id);
}
