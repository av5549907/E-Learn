package com.elearn.app.repositories;

import com.elearn.app.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category,String> {

    Optional<Category> findByTitle(String title);
    List<Category> findByAddedDate(Date date);
}
