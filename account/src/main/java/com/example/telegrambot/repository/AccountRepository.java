package com.example.telegrambot.repository;

import com.example.telegrambot.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> getAccountByChatId(String chatId);

}
