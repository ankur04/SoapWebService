/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soapwebservice.service;

import com.mycompany.soapwebservice.controllers.MoviesJpaController;
import com.mycompany.soapwebservice.controllers.exceptions.NonexistentEntityException;
import com.mycompany.soapwebservice.models.Movies;
import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ankur
 */

@WebService
public class MovieService {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Images_PU");
    MoviesJpaController moviesRepo = new MoviesJpaController(emf);
        
    
    @WebMethod
    public int insertMovies(String name, String category, byte[] data) throws Exception {
        
        Movies movies = new Movies();
        movies.setImage(data);
        movies.setName(name);
        movies.setType(category);
        movies.setId(BigDecimal.valueOf(moviesRepo.getMoviesCount() + 1));
        moviesRepo.create(movies);
        return data.length;
    }
    
    @WebMethod
    public boolean deleteMovie(int id) throws NonexistentEntityException {
        moviesRepo.destroy(BigDecimal.valueOf(id));
        return true;
    }
    
    @WebMethod
    public boolean updateMovie(int id, Movies movie) throws Exception {
        Movies movieFound = moviesRepo.findMovies(BigDecimal.valueOf(id));
        if (!movie.getName().isEmpty()) {
            movieFound.setName(movie.getName());
        }
        if (!movie.getType().isEmpty()) {
            movieFound.setType(movie.getType());
        }
        if (movie.getImage() != null) {
            movieFound.setImage(movie.getImage());
        }
        
        moviesRepo.edit(movieFound);
        return true;
    }
    
    @WebMethod
    public Movies getMovie(int id) {
        return moviesRepo.findMovies(BigDecimal.valueOf(id));
    }
    
    @WebMethod
    public List<Movies> getAllMovies() {
        return moviesRepo.findMoviesEntities();
    }
}
