package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value =
            """
                            SELECT tb_movie.id, tb_movie.title AS "title", tb_movie.sub_title AS "subTitle", tb_movie.movie_year AS "year", tb_movie.img_url AS "imgUrl"
                            FROM tb_movie
                            INNER JOIN tb_genre ON tb_movie.genre_id = tb_genre.id
                            WHERE tb_genre.id = :id
                    """)
    Page<MovieProjection> searchByGenreId(Long id, Pageable pageable);
}
