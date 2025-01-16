package com.elearn.app.repositories;

import com.elearn.app.entities.Category;
import com.elearn.app.entities.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepoTest {
    @Autowired
    CategoryRepo categoryRepo;
    Category category;
    Course courses;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByTitle() {
    }

    @Test
    void findByAddedDate() {
    }
}