package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.ReviewDTO;
import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getAllReviews();
    List<ReviewDTO> getReviewsByBookId(Integer bookId);
    ReviewDTO createReview(ReviewDTO reviewDTO);
    ReviewDTO updateReview(ReviewDTO reviewDTO);
    void deleteReview(Integer id);
}
