package com.example.telegrambot.controller;

import com.example.telegrambot.service.BotManagerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * BotCheckControllerTest.
 *
 * @author Maxim Solovyov
 */
@WebMvcTest(BotManagerController.class)
public class BotCheckControllerTest {

  @MockBean
  BotManagerService botManagerService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void check() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bot/check")
    ).andExpect(MockMvcResultMatchers.status().isOk());
  }

}
