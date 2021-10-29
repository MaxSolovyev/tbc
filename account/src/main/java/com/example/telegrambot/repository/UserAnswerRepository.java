package com.example.telegrambot.repository;

import com.example.telegrambot.model.Account;
import com.example.telegrambot.model.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Integer> {

    Optional<UserAnswer> getUserAnswerByAccountAndDoneFalse(Account account);

    @Query("select an from UserAnswer an LEFT JOIN FETCH an.account ac where ac.chatId = :chatId and an.done = false")
    Optional<UserAnswer> getNotDoneByChatId(String chatId);
}
