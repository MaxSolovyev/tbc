package com.example.telegrambot.repository;

import com.example.telegrambot.model.BotInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BotInfoRepository extends JpaRepository<BotInfo, Long> {

    List<BotInfo> findByName(String name);
}
