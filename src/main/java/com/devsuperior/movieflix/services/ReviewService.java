package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private UserService userService;

	@Transactional
	public ReviewDTO insertReview(ReviewDTO dto) {
		Review entity = new Review();
		entity.setText(dto.getText());
		Movie movie = movieRepository.getReferenceById(dto.getMovieId());
		entity.setMovie(movie);
		entity = reviewRepository.save(entity);

		UserDTO userDTO = userService.getProfile();
		User user = new User();
		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		entity.setUser(user);

		return new ReviewDTO(entity);
	}
}
