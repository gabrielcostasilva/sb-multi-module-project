package com.example.core;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public record Movie(String id, String title, String genre) { }
