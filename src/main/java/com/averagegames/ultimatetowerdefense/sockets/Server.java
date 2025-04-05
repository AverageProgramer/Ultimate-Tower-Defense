package com.averagegames.ultimatetowerdefense.sockets;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ApiStatus.Internal
@ClientHandler
public final class Server implements Runnable {

    private final ServerSocket server;

    private final LinkedList<ConnectionHandler> connections;

    private final ExecutorService pool;

    private boolean done;

    public Server(final int port) throws IOException {
        this.server = new ServerSocket(port);

        this.connections = new LinkedList<>();

        this.pool = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        try {
            while (!done) {
                Socket client = this.server.accept();

                ConnectionHandler handler = new ConnectionHandler(client);

                this.connections.add(handler);
                this.pool.execute(handler);
            }
        } catch (IOException ex) {
            this.shutdown();
        }
    }

    public void broadcast(@Nullable final String message) {
        for (ConnectionHandler handler : this.connections) {
            handler.sendMessage(message);
        }
    }

    public void shutdown() {
        this.done = true;

        try {
            if (!this.server.isClosed()) {
                this.server.close();
            }
        } catch (IOException ex) {
            // The exception does not need to be handled.
        }

        this.connections.forEach(ConnectionHandler::shutdown);
    }

    private class ConnectionHandler implements Runnable {

        private final Socket client;

        private final BufferedReader in;
        private final PrintWriter out;

        public ConnectionHandler(@NotNull final Socket client) throws IOException {
            this.client = client;

            this.in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
            this.out = new PrintWriter(this.client.getOutputStream(), true);
        }

        @Override
        public void run() {
            this.out.println(STR."You (\{this.client}) are connected!");

            try {
                String message;
                while ((message = this.in.readLine()) != null) {
                    System.out.println(message);
                    broadcast(STR."(\{this.client}): \{message}");
                }
            } catch (IOException ex) {
                this.shutdown();
            }
        }

        public void sendMessage(@Nullable final String message) {
            this.out.println(message);
        }

        public void shutdown() {
            try {
                if (!this.client.isClosed()) {
                    this.client.close();
                }

                in.close();
            } catch (IOException ex) {
                // The exception does not need to be handled.
            }

            out.close();
        }
    }
}
