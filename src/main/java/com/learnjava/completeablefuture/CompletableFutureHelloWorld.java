package com.learnjava.completeablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.LoggerUtil;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@RequiredArgsConstructor
public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public static void main(String[] args) {

        HelloWorldService helloWorldService = new HelloWorldService();

        supplyAsync(helloWorldService::helloWorld).thenApply(String::toUpperCase).thenAccept(LoggerUtil::log);

        log("Done");

        delay(2000);

    }

    public CompletableFuture<String> helloWorld() {

        return supplyAsync(helloWorldService::helloWorld).thenApply(s -> s.toUpperCase() + s.length());

    }

    public String helloWorldMultipleAsyncCalls() {

        startTimer();

        CompletableFuture<String> helloCompletableFuture = supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldCompletableFuture = supplyAsync(helloWorldService::world);

        CompletableFuture<String> hiCompletableFuture = supplyAsync(() -> {

            delay((1_000));
            return " Hi Completable Future!";

        });

        CompletableFuture<String> byeCompletableFuture = supplyAsync(() -> {

            delay((1_000));
            return " Bye!";

        });

        String helloWorld =  helloCompletableFuture.thenCombine(worldCompletableFuture, (hello, world) -> hello + world)
                                                   .thenCombine(hiCompletableFuture,
                                                           (helloWorldString, hiCompletableFutureString) ->
                                                                   helloWorldString + hiCompletableFutureString)
                                                   .thenCombine(byeCompletableFuture, (previous, current) -> previous +current)
                                                   .thenApply(String::toUpperCase).join();



        timeTaken();

        return helloWorld;

    }

    public CompletableFuture<String> helloWorldThenCompose() {

        return supplyAsync(helloWorldService::hello).thenCompose(helloWorldService::worldFuture)
                                                    .thenApply(String::toUpperCase);

    }

}
