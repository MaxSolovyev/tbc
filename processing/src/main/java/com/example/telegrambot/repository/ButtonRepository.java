package com.example.telegrambot.repository;

import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.ButtonNativeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ButtonRepository extends JpaRepository<Button, Long> {

    @Query("SELECT b FROM Button b LEFT JOIN FETCH b.reaction r WHERE b.name = :name")
    Optional<Button> findByName(String name);

    @Query("SELECT b FROM Button b LEFT JOIN FETCH b.reaction r WHERE b.id = :id")
    Optional<Button> findById(long id);

    @Query(value = "SELECT * FROM button WHERE button.id = :id", nativeQuery = true)
    List<Button> findNativeById(long id);
}