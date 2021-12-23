package com.example.telegrambot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * OrderControllerTest.
 *
 * @author Maxim Solovyov
 */
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
  @Autowired
  private MockMvc mockMvc;

//  @Test
//  public void check() throws Exception {
//    mockMvc.perform(MockMvcRequestBuilders.get("/bot/check")
//    ).andExpect(MockMvcResultMatchers.status().isOk());
//  }

  @DisplayName("Должен корректно вызываться get на /person")
  @Test
  void get() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/bot/check"))
        .andExpect(status().isOk());
  }


}
