package com.nikolay.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SocketService {

    private final SocketIOServer server;

    @Autowired
    public SocketService(SocketIOServer server) {
        this.server = server;
    }

    public Set<String> getRooms() {
        List<SocketIOClient> clientList = new ArrayList<>();
        clientList.addAll(server.getAllClients());
        Set<String> rooms = new HashSet<>();
        for (SocketIOClient client : clientList) {
            rooms.addAll(client.getAllRooms());
        }
        rooms.removeIf(r -> r.length() > 20 || r.length() < 1);
        return rooms;
    }

    public void shareRooms() {
        server.getBroadcastOperations().sendEvent(Actions.SHARE_ROOMS.label, getRooms());
    }
}
