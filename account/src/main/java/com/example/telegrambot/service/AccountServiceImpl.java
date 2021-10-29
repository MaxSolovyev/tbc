package com.example.telegrambot.service;

import com.example.telegrambot.dto.AccountRequest;
import com.example.telegrambot.dto.AccountResponse;
import com.example.telegrambot.dto.AccountStateRequest;
import com.example.telegrambot.dto.AccountStateResponse;
import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.Account;
import com.example.telegrambot.model.UserAnswer;
import com.example.telegrambot.repository.AccountRepository;
import com.example.telegrambot.repository.UserAnswerRepository;
import com.example.telegrambot.utils.EnumAccountState;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String START_CHECK_LIST_TYPE = "account";

    private final AccountRepository accountRepository;
    private final UserAnswerRepository userAnswerRepository;
//    private final QuestionRepository questionRepository;


    public AccountServiceImpl(AccountRepository accountRepository, UserAnswerRepository userAnswerRepository) {
        this.accountRepository = accountRepository;
        this.userAnswerRepository = userAnswerRepository;
//        this.questionRepository = questionRepository;
    }

    @Override
    public AccountResponse process(AccountRequest accountRequest) {
        AccountResponse response = new AccountResponse();

        Account account = accountRepository.getAccountByChatId(accountRequest.getChatId()).orElseGet(() -> {
            Account accountNew = new Account();
            accountNew.setChatId(accountRequest.getChatId());
            return accountRepository.save(accountNew);
        });
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAccount(account);
        userAnswer.setQuestionId(accountRequest.getNextQuestionId());
        userAnswer.setQuestion(accountRequest.getNextQuestion());
        userAnswerRepository.save(userAnswer);

        return response;
    }

    @Override
    public AccountStateResponse state(AccountStateRequest accountRequest) throws NotFoundException {
        AccountStateResponse accountStateResponse = new AccountStateResponse();
        Optional<Account> accountOptional = accountRepository.getAccountByChatId(accountRequest.getChatId());
        if (accountOptional.isPresent()) {
            Optional<UserAnswer> userAnswerOptional = userAnswerRepository.getNotDoneByChatId(accountRequest.getChatId());
            if (userAnswerOptional.isPresent()) {
                UserAnswer userAnswer = userAnswerOptional.get();
                userAnswer.setAnswer(accountRequest.getAnswer());
                userAnswer.setDone(true);
                userAnswerRepository.save(userAnswer);
                accountStateResponse.setCurrentQuestion(userAnswerOptional.get().getQuestionId());
            }
        }

        return accountStateResponse;
    }

    private boolean checkFillingAccount(String chatId) {
        Optional<Account> accountOptional = accountRepository.getAccountByChatId(chatId);
        if (accountOptional.isPresent()) {
            //проверяем в каком статусе
            Account account = accountOptional.get();
            if (account.getState() == EnumAccountState.New) {
                //
            }
        } else {
            //аккаунта еще нет. Создаем его в статусе New
            Account account = new Account();
            account.setState(EnumAccountState.New);
            account.setChatId(chatId);
            accountRepository.save(account);
        }

        return true;
    }

//    private Question getQuestionForStartCheckList() {
//        return questionRepository.getFirstByCheckListType(START_CHECK_LIST_TYPE).orElse(null);
//    }
}
