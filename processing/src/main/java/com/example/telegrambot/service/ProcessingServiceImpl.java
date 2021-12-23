package com.example.telegrambot.service;

import com.example.telegrambot.client.AccountReactiveService;
import com.example.telegrambot.dto.*;
import com.example.telegrambot.exception.NotFoundException;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.model.keyboard.ButtonNativeDto;
import com.example.telegrambot.model.keyboard.KeyBoard;
import com.example.telegrambot.model.keyboard.Reaction;
import com.example.telegrambot.model.mapper.ButtonMapper;
import com.example.telegrambot.model.mapper.KeyBoardMapper;
import com.example.telegrambot.model.question.CheckList;
import com.example.telegrambot.model.question.Question;
import com.example.telegrambot.repository.ButtonRepository;
import com.example.telegrambot.repository.KeyBoardRepository;
import com.example.telegrambot.repository.QuestionRepository;
import com.example.telegrambot.utils.KeyBoardType;
import com.example.telegrambot.utils.ReactionType;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.util.StringUtils;

@Service
public class ProcessingServiceImpl implements ProcessingService {
    private static final String FIRST_KEYBOARD = "main_menu";

    private final ButtonServiceSupplier buttonServiceSupplier;
    private final ButtonRepository buttonRepository;
    private final KeyBoardRepository keyBoardRepository;
    private final AccountReactiveService accountReactiveService;
    private final QuestionRepository questionRepository;
    private final EntityManager entityManager;

    public ProcessingServiceImpl(ButtonServiceSupplier buttonServiceSupplier,
                KeyBoardRepository keyBoardRepository,
                AccountReactiveService accountReactiveService,
                QuestionRepository questionRepository,
                ButtonRepository buttonRepository,
                EntityManager entityManager) {
        this.buttonServiceSupplier = buttonServiceSupplier;
        this.keyBoardRepository = keyBoardRepository;
        this.accountReactiveService = accountReactiveService;
        this.questionRepository = questionRepository;
        this.buttonRepository = buttonRepository;
        this.entityManager = entityManager;
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

    @Override
    public ButtonDto getButton(int buttonId) {
        Optional<Button> buttonOptional = buttonRepository.findById((long) buttonId);

        if (buttonOptional.isEmpty()) {
            return null;
        } else {
            return ButtonMapper.instance.toDto(buttonOptional.get());
        }
    }

    public ButtonDto getNativeById(int buttonId) {
        Specification<ButtonDto> spec;

        var list = Collections.singletonList("1");

        return ButtonMapper.instance.toDto(buttonRepository.findNativeById((long) buttonId).get(0));
    }

    @Override
    public List<Object[]> getViaCriteriaById(int buttonId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Button> fromClause = criteriaQuery.from(Button.class);
        Join<Button, KeyBoard> joinClause = fromClause.join("keyBoard", JoinType.LEFT);
        Selection<?> keyBoardName = joinClause.get("name");
        Selection<?> buttonName = fromClause.get("name");
        Selection<KeyBoardType> keyBoardType = joinClause.get("type");
        criteriaQuery.select(criteriaBuilder.array(keyBoardName, keyBoardType, buttonName));

        TypedQuery<Object[]> query = entityManager.createQuery(criteriaQuery);
        List<Object[]> results = query.getResultList();

        return results;
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Button> criteriaQuery = criteriaBuilder.createQuery(Button.class);
//        Root<Button> itemRoot = criteriaQuery.from(Button.class);
//        Predicate predicateForId = criteriaBuilder.equal(itemRoot.get("id"), buttonId);
//        criteriaQuery.where(predicateForId);
//        return entityManager.createQuery(criteriaQuery).getResultList().stream().map(
//            ButtonMapper.instance::toDto).collect(Collectors.toList());
    }

    public static Specification<ButtonDto> getSpec() {

        return (r, cq, cb) -> {
            CriteriaBuilder criteriaBuilder = null;
            return criteriaBuilder.equal(null, 1);
        };
    }

    static class SpecificationImpl<ButtonDto> implements Specification<com.example.telegrambot.dto.ButtonDto> {

        @Override
        public Predicate toPredicate(Root<com.example.telegrambot.dto.ButtonDto> root,
            CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            return null;
        }

        @Override
        public Specification<com.example.telegrambot.dto.ButtonDto> or(
            Specification<com.example.telegrambot.dto.ButtonDto> other) {
            return Specification.super.or(other);
        }
    }
}
