package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.ReviewDTO;
import com.sliit.oop_serverapp.entity.Review;
import com.sliit.oop_serverapp.entity.User;
import com.sliit.oop_serverapp.entity.Book;
import com.sliit.oop_serverapp.exception.ResourceNotFoundException;
import com.sliit.oop_serverapp.repository.ReviewRepository;
import com.sliit.oop_serverapp.repository.UserRepository;
import com.sliit.oop_serverapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OOP Concept: Abstraction & Polymorphism
 * ReviewServiceImpl implements ReviewService, managing the lifecycle 
 * of user feedback and interactions with the archive.
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByBookId(Integer bookId) {
        return reviewRepository.findByBookId(bookId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = new Review();
        updateEntityFromDTO(review, reviewDTO);
        return convertToDTO(reviewRepository.save(review));
    }

    @Override
    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(reviewDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewDTO.getId()));
        updateEntityFromDTO(review, reviewDTO);
        return convertToDTO(reviewRepository.save(review));
    }

    @Override
    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        if (review.getUser() != null) dto.setUserId(review.getUser().getId());
        if (review.getBook() != null) dto.setBookId(review.getBook().getId());
        return dto;
    }

    private void updateEntityFromDTO(Review review, ReviewDTO dto) {
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
            review.setUser(user);
        }
        if (dto.getBookId() != null) {
            Book book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + dto.getBookId()));
            review.setBook(book);
        }
    }
}
