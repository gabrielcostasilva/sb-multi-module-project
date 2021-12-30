package com.example.reactivedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

import com.example.core.Movie;
import com.example.core.MovieEvent;

@SpringBootApplication
public class ReactiveDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveDataApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        var client = WebClient.create();

        client.get()
                .uri("http://localhost:8080/movies")
                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(Movie.class))
                .filter(movie -> movie.title().toLowerCase().contains("flux".toLowerCase()))
                .subscribe(movie ->
                        client.get()
                                .uri("http://localhost:8080/movies/{id}/events", movie.id())
                                .exchangeToFlux(clientResponse -> clientResponse.bodyToFlux(MovieEvent.class))
                                .subscribe(System.out::println));
    }

}
