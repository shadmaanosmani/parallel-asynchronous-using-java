package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.*;

class CompletableFutureHelloWorldTest {

    private final HelloWorldService helloWorldService = new HelloWorldService();

    private final CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void helloWorld() {

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorld();

        completableFuture.thenAccept(s -> assertEquals("HELLO WORLD" + "HELLO WORLD".length(), s)).join();

    }

    @Test
    void helloWorldMultipleAsyncCalls() {

        String helloWorld = completableFutureHelloWorld.helloWorldMultipleAsyncCalls();

        assertEquals("HELLO WORLD! HI COMPLETABLE FUTURE! BYE!", helloWorld);

    }

    @Test
    void helloWorldThenCompose() {

        startTimer();

        CompletableFuture<String> completableFuture = completableFutureHelloWorld.helloWorldThenCompose();

        completableFuture.thenAccept(s -> assertEquals("HELLO WORLD!", s)).join();

        timeTaken();

    }

}