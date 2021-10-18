package com.example.telegrambot.repository;

import com.example.telegrambot.model.keyboard.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}
