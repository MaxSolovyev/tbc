package com.example.telegrambot.controller;

import com.example.telegrambot.client.AccountReactiveService;
import com.example.telegrambot.dto.ButtonDto;
import com.example.telegrambot.model.keyboard.Button;
import com.example.telegrambot.repository.ButtonRepository;
import com.example.telegrambot.repository.KeyBoardRepository;
import com.example.telegrambot.repository.QuestionRepository;
import com.example.telegrambot.repository.ReactionRepository;
import com.example.telegrambot.service.ButtonServiceSupplier;
import com.example.telegrambot.service.ProcessingServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;

@DisplayName("Controller ProcessingController")
@WebFluxTest(controllers = ProcessingController.class)
@ContextConfiguration(classes = ProcessingControllerTest.class)
//@Import(ProcessingServiceImpl.class)
public class ProcessingControllerTest {

    @Configuration
    @Import(value = {ProcessingServiceImpl.class})
    static class ContextConfiguration {}

    @MockBean
    ButtonRepository buttonRepository;
    @MockBean
    ButtonServiceSupplier buttonServiceSupplier;
    @MockBean
    KeyBoardRepository keyBoardRepository;
    @MockBean
    AccountReactiveService accountReactiveService;
    @MockBean
    QuestionRepository questionRepository;
    @MockBean
    ReactionRepository reactionRepository;

    @Autowired
    WebTestClient webTestClient;

    @DisplayName("Should get expected ButtonDto '/getButton' by id")
    @Test
    void getButtonById() {
        Mockito.when(buttonRepository.findById(anyLong())).thenReturn(
            Optional.of(new Button(1, 1, "test")));
        FluxExchangeResult<ButtonDto> result =
                webTestClient.get()
                        .uri("/getButton/{id}", Map.of("id", 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(ButtonDto.class);
        Assertions.assertEquals("test", result.getResponseBody().blockFirst().getName());
    }
}
