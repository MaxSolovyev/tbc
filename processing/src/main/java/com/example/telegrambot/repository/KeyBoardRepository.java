package com.example.telegrambot.repository;

import com.example.telegrambot.model.keyboard.KeyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface KeyBoardRepository extends JpaRepository<KeyBoard, Long> {

    @Query("SELECT u FROM KeyBoard u LEFT JOIN FETCH u.buttons b LEFT JOIN FETCH b.reaction r WHERE u.name = :name")
    Optional<KeyBoard> findByNameWithButtons(String name);

    @Query("SELECT u FROM KeyBoard u LEFT JOIN FETCH u.buttons WHERE u.id = :id")
    Optional<KeyBoard> findByIdWithButtons(long id);
}
