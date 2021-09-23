package com.example.telegrambot.repository;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.utils.BotType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("BotInfo repository by jpa")
@DataJpaTest
public class BotInfoRepositoryTest {

    @Autowired
    private BotInfoRepository botInfoRepository;

    @Autowired
    TestEntityManager entityManager;

    @DisplayName("Should return empty list BotInfo by name if it's not persistence")
    @Test
    void shouldReturnEmptyListBeforeAnyPersist() {
        var listBotInfo = botInfoRepository.findByName("bot");
        assertThat(listBotInfo).isEmpty();
    }

    @DisplayName("Should return correct BotInfo by name after persist")
    @Test
    void shouldReturnCorrectBotInfoAfterPersist() {
        entityManager.persist(new BotInfo("bot", "pass", BotType.COMMAND));
        var listBotInfo = botInfoRepository.findByName("bot");
        assertEquals("pass", listBotInfo.get(0).getToken());
    }

}
