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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@DisplayName("Controller BotManagerController")
@WebMvcTest(BotManagerController.class)
public class BotManagerControllerTest {

    @MockBean
    BotManagerService botManagerService;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("Should invoke '/bot/register' correctly")
    @Test
    void register() throws Exception {
        Mockito.doNothing().when(botManagerService).register(any(BotInfo.class));
        ObjectMapper mapper = new ObjectMapper();
        BotInfo botInfo = new BotInfo("bot", "pass", BotType.COMMAND);
        mockMvc.perform(MockMvcRequestBuilders.post("/bot/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(botInfo))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Should invoke '/bot/unregister/{botName}' correctly")
    @Test
    void unregister() throws Exception {
        Mockito.doNothing().when(botManagerService).unregister(anyString());
        mockMvc.perform(MockMvcRequestBuilders.get("/bot/unregister/{botName}", "bot")
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("Should return correct data from '/bot/readAll'")
    @Test
    void getAll() throws Exception {
        Mockito.when(botManagerService.getAll()).thenReturn(List.of(new BotDto("bot", "pass", BotType.COMMAND)));
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/bot/readAll", "bot"))
                .andReturn();
        Assertions.assertAll("Should return correctly response",
                () -> Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(), "status is OK"),
                () -> Assertions.assertEquals(MediaType.APPLICATION_JSON.toString(), result.getResponse().getContentType(), "content type is correct"),
                () -> Assertions.assertEquals("bot", mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<BotDto>>() {}).get(0).getName(), "returned data is expected")
        );
    }

}
