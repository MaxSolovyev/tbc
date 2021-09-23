package com.example.telegrambot.controller;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class WebHookController {

    @PostMapping("/webhook")
    public FulFillment webhook(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        String name = new JSONObject(body)
                .getJSONObject("queryResult")
                .getJSONObject("parameters")
                .getJSONObject("person")
                .getString("name");

        return new FulFillment("Welcome on board, " + name);
    }

    static class FulFillment {
        private String fulfillmentText;

        public FulFillment(String fulfillmentText) {
            this.fulfillmentText = fulfillmentText;
        }
    }
}