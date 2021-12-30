package com.example.service;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.core.Movie;

public interface MovieRepository extends ReactiveMongoRepository<Movie, String> {}
