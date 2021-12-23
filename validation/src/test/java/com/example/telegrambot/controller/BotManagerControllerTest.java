package com.example.telegrambot.controller;

import com.example.telegrambot.model.BotInfo;
import com.example.telegrambot.model.dto.BotDto;
import com.example.telegrambot.service.BotManagerService;
import com.example.telegrambot.utils.BotType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@DisplayName("Controller BotManagerController")
@ExtendWith(SpringExtension.class)
@WebFluxTest(BotManagerController.class)
@Import(BotManagerService.class)
public class BotManagerControllerTest {

    @MockBean
    BotManagerService botManagerService;

    @Autowired
    WebTestClient webTestClient;

    @DisplayName("Should invoke '/bot/register' correctly")
    @Test
    void register() {
        Mockito.doNothing().when(botManagerService).register(any(BotInfo.class));
        ObjectMapper mapper = new ObjectMapper();
        BotInfo botInfo = new BotInfo("bot", "pass", BotType.COMMAND);
        webTestClient.post()
                .uri("/bot/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(botInfo))
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("Should invoke '/bot/unregister/{botName}' correctly")
    @Test
    void unregister() {
        Mockito.doNothing().when(botManagerService).unregister(anyString());

        webTestClient.get()
                .uri("/bot/unregister/{botName}", Map.of("botName", "bot"))
                .exchange()
                .expectStatus().isOk();
    }

    @DisplayName("Should return correct data from '/bot/readAll'")
    @Test
    void getAll() {
        Mockito.when(botManagerService.getAll()).thenReturn(List.of(new BotDto("bot", "pass", BotType.COMMAND)));
        ObjectMapper mapper = new ObjectMapper();

        FluxExchangeResult<String> result =
                webTestClient.get()
                .uri("/bot/readAll")
                .exchange()
                        .returnResult(String.class);

        Assertions.assertAll("Should return correctly response",
                () -> Assertions.assertEquals(HttpStatus.OK, result.getStatus(), "status is OK"),
                () -> Assertions.assertEquals(MediaType.APPLICATION_JSON, result.getResponseHeaders().getContentType(), "content type is correct"),
                () -> Assertions.assertEquals("bot", mapper.readValue(result.getResponseBody().blockFirst(), new TypeReference<List<BotDto>>() {}).get(0).getName(), "returned data is expected")
        );
    }

}
