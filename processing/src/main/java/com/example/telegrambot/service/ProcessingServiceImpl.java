package com.example.telegrambot.service;

import com.example.telegrambot.client.AccountReactiveService;
import com.example.telegrambot.dto.*;
import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.Reaction;
import com.example.telegrambot.model.mapper.KeyBoardMapper;
import com.example.telegrambot.model.question.CheckList;
import com.example.telegrambot.model.question.Question;
import com.example.telegrambot.repository.KeyBoardRepository;
import com.example.telegrambot.repository.QuestionRepository;
import com.example.telegrambot.utils.ReactionType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProcessingServiceImpl implements ProcessingService {
    private static final String FIRST_KEYBOARD = "main_menu";

    private final ButtonServiceSupplier buttonServiceSupplier;
    private final KeyBoardRepository keyBoardRepository;
    private final AccountReactiveService accountReactiveService;
    private final QuestionRepository questionRepository;

    public ProcessingServiceImpl(ButtonServiceSupplier buttonServiceSupplier,
                                 KeyBoardRepository keyBoardRepository,
                                 AccountReactiveService accountReactiveService,
                                 QuestionRepository questionRepository) {
        this.buttonServiceSupplier = buttonServiceSupplier;
        this.keyBoardRepository = keyBoardRepository;
        this.accountReactiveService = accountReactiveService;
        this.questionRepository = questionRepository;
    }

    @Override
    public KeyBoardResponse process(ProcessingRequest request) throws NotFoundException {
        KeyBoardResponse keyBoardResponse = new KeyBoardResponse();
        keyBoardResponse.setType(ReactionType.TEXT);

        Question nextQuestion = null;
        String userAnswer = request.getRequest();
        if (request.getRequestType() == ReactionType.KEYBOARD) {
            Button button = buttonServiceSupplier.getButtonById(Long.parseLong(request.getRequest()));
            userAnswer = button.getName();
            Reaction reaction = button.getReaction();
            if (reaction.getType() == ReactionType.NEXTQUESTION) {
                nextQuestion = reaction.getNextQuestion();
            } else if (reaction.getType() == ReactionType.KEYBOARD) {
                keyBoardRepository.findByIdWithButtons(reaction.getKeyboard().getId()).ifPresentOrElse(
                        keyBoard -> keyBoardResponse.setKeyboard(KeyBoardMapper.instance.toDto(keyBoard)),
                        NotFoundException::new);
                keyBoardResponse.setType(ReactionType.KEYBOARD);
                return keyBoardResponse;
            } else if (reaction.getType() == ReactionType.TEXT) {
                keyBoardResponse.setType(ReactionType.TEXT);
                keyBoardResponse.setText(reaction.getText());
                return keyBoardResponse;
            }
        }
//        switch (request.getRequestType()) {
//            case KEYBOARD:
//
//                keyBoardResponse.setType(reaction.getType());
//                switch (keyBoardResponse.getType()) {
//                    case TEXT:
//                        keyBoardResponse.setText(reaction.getText());
//                        break;
//                    case KEYBOARD:
//                        keyBoardRepository.findByIdWithButtons(reaction.getKeyboard().getId()).ifPresentOrElse(
//                                button -> keyBoardResponse.setKeyboard(KeyBoardMapper.instance.toDto(button)),
//                                NotFoundException::new);
//                        break;
//                }
//
//                break;
//            case TEXT:
//                break;
//        }
        AccountStateRequest accountStateRequest = new AccountStateRequest();
        accountStateRequest.setAnswer(userAnswer);
        accountStateRequest.setChatId(request.getChatId());
        AccountStateResponse accountStateResponse = accountReactiveService.state(accountStateRequest).block();

        if (nextQuestion == null) {
            //если вопрос не определен ранее из кнопки ссылающейся на следующий вопрос, то
            //определяем его как ледующей после текущего
            if (accountStateResponse.getCurrentQuestion() > 0) {
                Optional<Question> questionOptional = questionRepository.getQuestionById(accountStateResponse.getCurrentQuestion());
                if (questionOptional.isPresent()) {
                    Question currentQuestion = questionOptional.get();
                    CheckList curCheckList = currentQuestion.getCheckList();
                    nextQuestion = questionRepository.getFirstByCheckListAndOrderIsAfter(curCheckList, currentQuestion.getOrder()).orElse(null);
                }
            } else {
                //если на стороне аккаунта нет прогресса, то используем стартовое меню
                keyBoardResponse.setType(ReactionType.KEYBOARD);
                keyBoardResponse.setKeyboard(KeyBoardMapper.instance.toDto(keyBoardRepository.findByNameWithButtons(FIRST_KEYBOARD).orElseThrow(() -> new NotFoundException("start menu keyboard is not found"))));
            }
        }

//        if (request.getRequest().isEmpty()) {
//            keyBoardRepository.findByNameWithButtons(FIRST_KEYBOARD).ifPresentOrElse(keyBoard -> {
//                keyBoardResponse.setType(ReactionType.KEYBOARD);
//                KeyBoardDto keyBoardDto = KeyBoardMapper.instance.toDto(keyBoard);
//                keyBoardResponse.setKeyboard(keyBoardDto);
//            }, NotFoundException::new);
//        } else {
//
//
//        }
        if (nextQuestion != null) {
            keyBoardResponse.setText(nextQuestion.getQuestion());
            if (nextQuestion.getAnswerType() == ReactionType.KEYBOARD) {
                keyBoardRepository.findByIdWithButtons(nextQuestion.getAnswerKeyBoard().getId()).ifPresentOrElse(
                        keyBoard -> keyBoardResponse.setKeyboard(KeyBoardMapper.instance.toDto(keyBoard)),
                        NotFoundException::new);

//                KeyBoardDto keyBoardDto = KeyBoardMapper.instance.toDto(nextQuestion.getAnswerKeyBoard());
                keyBoardResponse.setType(ReactionType.KEYBOARD);
//                keyBoardResponse.setKeyboard(keyBoardDto);
            }
            accountReactiveService.process(new AccountRequest(request.getChatId(), nextQuestion.getId())).subscribe();
        }

        return keyBoardResponse;
    }
}
