package ch.taburett.tichu.models;


import java.util.concurrent.*;

public class ProxyPlayer {
    public CompletableFuture<String> closeConnection() {

        System.out.println("Creating Future " );
        CompletableFuture<String> future = CompletableFuture.supplyAsync(
                () -> {
                    try {
                        Thread.sleep(342);
                        if (ThreadLocalRandom.current().nextBoolean())
                            return "Logout Successful";
                        else
                            throw new RuntimeException("Failed");

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

        return future;
    }
}
