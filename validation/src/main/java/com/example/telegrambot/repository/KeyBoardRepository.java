package com.example.telegrambot.repository;

import com.example.telegrambot.model.keyboard.KeyBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyBoardRepository extends JpaRepository<KeyBoard, Long> {

    List<KeyBoard> findByName(String name);
}
