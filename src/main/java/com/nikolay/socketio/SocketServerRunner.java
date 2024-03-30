package com.nikolay.socketio;

import com.corundumstudio.socketio.SocketIOServer;
import com.nikolay.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocketServerRunner implements CommandLineRunner {

    private final SocketIOServer server;
//    @Autowired
//    private GuestRepository guestRepository;

    @Autowired
    public SocketServerRunner(SocketIOServer server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
//        guestRepository.deleteAll();
    }
}
