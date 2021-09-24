package com.example.telegrambot.repository;

import com.example.telegrambot.model.keyboard.Button;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButtonRepository extends JpaRepository<Button, Long> {
}
