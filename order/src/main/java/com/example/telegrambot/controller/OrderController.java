package com.example.telegrambot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @GetMapping("/bot/check")
    public String check() {
        return "check ok";
    }

    @GetMapping("/bot/mock")
    public String mock() {
        return "test mock success";
    }

    @GetMapping("/bot/update")
    public String update() {
        return "update success";
    }

    //1

    //2

    //3

    //4

    //5

    //6

    //7

    //8

    //9

    //10

    // коммит 11

    // коммит 12
}
