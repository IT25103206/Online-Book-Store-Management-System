package com.sliit.oop_serverapp.controller;

import com.sliit.oop_serverapp.dto.ReviewDTO;
import com.sliit.oop_serverapp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * OOP Concept: Encapsulation & Abstraction
 * ReviewController manages user feedback.
 * It encapsulates review processing logic via the ReviewService.
 */
@RestController // Marks this class as a RESTful controller where every method returns a domain object instead of a view
@RequestMapping("/Reviews") // Base URI path mapping for all endpoints in this controller
@CrossOrigin // Enables Cross-Origin Resource Sharing (CORS) allowing frontend apps to connect to this backend
public class ReviewController {

    @Autowired // Field injection to automatically wire the ReviewService bean into this controller
    private ReviewService reviewService;

    /**
     * HTTP GET request to fetch all reviews.
     * URI: GET /Reviews
     * * @return List of ReviewDTO objects
     */
    @GetMapping
    public List<ReviewDTO> getAll() {
        return reviewService.getAllReviews();
    }

    /**
     * HTTP GET request to fetch reviews associated with a specific book ID.
     * URI: GET /Reviews/book/{bookId}
     * * @param bookId Unique identifier of the book passed via the URL path
     * @return List of ReviewDTO objects filtered by book ID
     */
    @GetMapping("/book/{bookId}")
    public List<ReviewDTO> getByBook(@PathVariable Integer bookId) {
        return reviewService.getReviewsByBookId(bookId);
    }

    /**
     * HTTP POST request to submit/create a new review.
     * URI: POST /Reviews/Add
     * * @param reviewDTO Data transfer object containing review details sent in the request body
     * @return ResponseEntity containing the created ReviewDTO and an HTTP 200 OK status
     */
    @PostMapping("/Add")
    public ResponseEntity<ReviewDTO> addReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO));
    }

    /**
     * HTTP PUT request to modify an existing review.
     * URI: PUT /Reviews/Update
     * * @param reviewDTO Data transfer object containing updated review details sent in the request body
     * @return ResponseEntity containing the updated ReviewDTO and an HTTP 200 OK status
     */
    @PutMapping("/Update")
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDTO) {
        return ResponseEntity.ok(reviewService.updateReview(reviewDTO));
    }

    /**
     * HTTP DELETE request to remove a review by its ID.
     * URI: DELETE /Reviews/delete/{id}
     * * @param id Unique identifier of the review to be deleted passed via the URL path
     * @return ResponseEntity containing a success message string and an HTTP 200 OK status
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review Deleted Successfully");
    }
}