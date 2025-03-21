package com.averagegames.ultimatetowerdefense.sockets;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public final class Client implements Runnable {

    private final Socket client;

    private final BufferedReader in;

    private final PrintWriter out;

    public Client(@Nullable final String host, final int port) throws IOException {
        this.client = new Socket(host, port);

        this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
        this.out = new PrintWriter(client.getOutputStream(), true);
    }

    public void shutdown() {
        try {
            if (!this.client.isClosed()) {
                this.client.close();
            }

            this.in.close();
            this.out.close();
        } catch (IOException ex) {
            // The exception does not need to be handled.
        }
    }

    @Override
    public void run() {
        this.out.println("Hello server!");

        try {
            System.out.println(this.in.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ClientHandler
    public static class Server {
        public Server() {

        }
    }
}
