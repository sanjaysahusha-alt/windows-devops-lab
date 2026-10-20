package com.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class App {
    static String greeting() {
        return "DevOps pipeline is working!";
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8080), 0
        );

        server.createContext("/health", exchange -> {
            byte[] body = "OK".getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, body.length);
            try (var out = exchange.getResponseBody()) {
                out.write(body);
            }
        });

        server.createContext("/", exchange -> {
            byte[] body = greeting().getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set(
                "Content-Type", "text/plain; charset=UTF-8"
            );
            exchange.sendResponseHeaders(200, body.length);
            try (var out = exchange.getResponseBody()) {
                out.write(body);
            }
        });

        server.start();
        System.out.println("DevOps demo listening on port 8080");
    }
}