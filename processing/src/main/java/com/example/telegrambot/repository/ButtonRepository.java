package com.example.telegrambot.repository;

import com.example.telegrambot.model.keyboard.Button;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ButtonRepository extends JpaRepository<Button, Long> {

    @Query("SELECT b FROM Button b LEFT JOIN FETCH b.reaction r WHERE b.name = :name")
    Optional<Button> findByName(String name);
}
