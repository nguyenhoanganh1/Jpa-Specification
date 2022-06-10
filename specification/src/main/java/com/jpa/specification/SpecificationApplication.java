package com.jpa.specification;

import com.jpa.specification.entities.Movie;
import com.jpa.specification.repositories.MovieRepository;
import com.jpa.specification.repositories.specifications.MovieSpecificationImpl;
import com.jpa.specification.repositories.specifications.SearchCriteria;
import com.jpa.specification.repositories.specifications.SearchOperation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@SpringBootApplication
public class SpecificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpecificationApplication.class, args);
    }

    @Bean
    public CommandLineRunner specificationsDemo(MovieRepository movieRepository) {
        return args -> {
//
//            // create new movies
//            movieRepository.saveAll(Arrays.asList(
//                    new Movie("Troy", "Drama", 7.2, 196, 2004),
//                    new Movie("The Godfather", "Crime", 9.2, 178, 1972),
//                    new Movie("Invictus", "Sport", 7.3, 135, 2009),
//                    new Movie("Black Panther", "Action", 7.3, 135, 2018),
//                    new Movie("Joker", "Drama", 8.9, 122, 2018),
//                    new Movie("Iron Man", "Action", 8.9, 126, 2008)
//            ));

            // search movies by `genre`
            MovieSpecificationImpl msGenre = new MovieSpecificationImpl();
            msGenre.add(new SearchCriteria("genre", "Action", SearchOperation.EQUAL));
            List<Movie> msGenreList = movieRepository.findAll(msGenre);
            msGenreList.forEach(System.out::println);
            System.out.println("======================================");

            // search movies by `title` and `rating` > 7
            MovieSpecificationImpl msTitleRating = new MovieSpecificationImpl();
            msTitleRating.add(new SearchCriteria("title", "black", SearchOperation.MATCH));
            msTitleRating.add(new SearchCriteria("rating", 7, SearchOperation.GREATER_THAN));
            List<Movie> msTitleRatingList = movieRepository.findAll(msTitleRating);
            msTitleRatingList.forEach(System.out::println);
            System.out.println("======================================");

            // search movies by release year < 2010 and rating > 8
            MovieSpecificationImpl msYearRating = new MovieSpecificationImpl();
            msYearRating.add(new SearchCriteria("releaseYear", 2010, SearchOperation.LESS_THAN));
            msYearRating.add(new SearchCriteria("rating", 8, SearchOperation.GREATER_THAN));
            List<Movie> msYearRatingList = movieRepository.findAll(msYearRating);
            msYearRatingList.forEach(System.out::println);
            System.out.println("======================================");

            // search movies by watch time >= 150 and sort by `title`
            MovieSpecificationImpl msWatchTime = new MovieSpecificationImpl();
            msWatchTime.add(new SearchCriteria("watchTime", 150, SearchOperation.GREATER_THAN_EQUAL));
            List<Movie> msWatchTimeList = movieRepository.findAll(msWatchTime, Sort.by("title"));
            msWatchTimeList.forEach(System.out::println);
            System.out.println("======================================");

            // search movies by `title` <> 'white' and paginate results
            MovieSpecificationImpl msTitle = new MovieSpecificationImpl();
            msTitle.add(new SearchCriteria("title", "white", SearchOperation.NOT_EQUAL));

            Pageable pageable = PageRequest.of(0, 3, Sort.by("releaseYear").descending());
            Page<Movie> msTitleList = movieRepository.findAll(msTitle, pageable);
            msTitleList.forEach(System.out::println);
        };
    }
}
