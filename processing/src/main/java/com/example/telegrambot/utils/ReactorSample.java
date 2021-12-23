package com.example.telegrambot.utils;

import reactor.core.publisher.Flux;

import java.util.Objects;

public class ReactorSample {
    public static void main(String[] args) throws InterruptedException {
//        Flux.<String>generate(sink -> {
//                    sink.next("hi");
//                })
//                .delayElements(Duration.ofMillis(500))
//                .take(4)
//                .subscribe(System.out::println);
//        Thread.sleep(4000L);

        Objects.nonNull(null);
        Flux.generate(() -> 2354,
                (state, sink) -> {
                    if (state > 2366) {
                        sink.complete();
                    } else {
                        sink.next("Step: " + state);
                    }
                    return state + 3;
                })
                .subscribe(System.out::println);
    }
}
