package sg.com.smartinventory.serviceImpls;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sg.com.smartinventory.entities.Review;
import sg.com.smartinventory.exceptions.RatingNotFoundException;
import sg.com.smartinventory.exceptions.ReviewNotFoundException;
import sg.com.smartinventory.repositories.ReviewRepository;
import sg.com.smartinventory.services.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {
  private ReviewRepository reviewRepository;

  // @Autowired
  public ReviewServiceImpl(ReviewRepository reviewRepository) {
    this.reviewRepository = reviewRepository;
  }

  // - - - POST - - -
  @Override
  public Review createReview(Review review) {
    Review newReview = reviewRepository.save(review);

    return newReview;
  }

  // - - - GET - - -
  @Override
  public ArrayList<Review> searchCustomerReviews(long customerId) {
    List<Review> foundReviews = reviewRepository.findByCustomerId(customerId);
    return (ArrayList<Review>) foundReviews;
  }

  @Override
  public ArrayList<Review> searchProductReviews(long productId) {
    List<Review> foundReviews = reviewRepository.findByProductId(productId);
    return (ArrayList<Review>) foundReviews;
  }

  @Override
  public ArrayList<Review> getRatings(int id) {
    if (reviewRepository.findByRating(id).isEmpty()) {
      throw new RatingNotFoundException(id);
    }
    List<Review> reviews = reviewRepository.findByRating(id);
    return (ArrayList<Review>) reviews;
  }

  @Override
  public Review getReview(Long id) {
    return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
  }

  @Override
  public ArrayList<Review> getAllReviews() {
    List<Review> allReviews = reviewRepository.findAll();
    return (ArrayList<Review>) allReviews;
  }

  // - - - PUT - - -
  @Override
  public Review updateReview(Long id, Review review) {
    // Retrieve the review from the database.
    Review reviewToUpdate = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));

    // Update the review retrieved from the database.
    reviewToUpdate.setCategory(review.getCategory());
    reviewToUpdate.setReviewContent(review.getReviewContent());
    reviewToUpdate.setRating(review.getRating());
    reviewToUpdate.setProduct(review.getProduct());

    // Save the updated review back to the database.
    return reviewRepository.save(reviewToUpdate);
  }

  // - - - DELETE - - -
  @Override
  public void deleteReview(long id) {
    reviewRepository.deleteById(id);
  }
}