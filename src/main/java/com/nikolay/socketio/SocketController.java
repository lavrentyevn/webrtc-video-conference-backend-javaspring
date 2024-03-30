package com.nikolay.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.nikolay.exceptions.UserAlreadyInRoomException;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SocketController {

    private final SocketIOServer server;
    private final SocketService socketService;

    @Autowired
    public SocketController(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;



        // event listeners

        server.addEventListener("username", String.class, (socket, s, ackRequest) -> {
            socket.set("username", s);
        });

        server.addEventListener(Actions.JOIN.label, DataDTO.class, (socket, dataDTO, ackRequest) -> {
            String roomID = dataDTO.getRoom();
            Set<String> joinedRooms = socket.getAllRooms();
            if (joinedRooms.contains(roomID)) throw new UserAlreadyInRoomException();

            List<SocketIOClient> clients = new ArrayList<>();
            clients.addAll(server.getAllClients());
            clients.removeIf(socketIOClient -> !socketIOClient.getAllRooms().contains(dataDTO.getRoom())); // remain only clients that are in specific room


            for (SocketIOClient clientID : clients) {
                server.getClient(clientID.getSessionId()).sendEvent(Actions.ADD_PEER.label, new DataDTO(socket.getSessionId().toString(), socket.get("username").toString(), false ));
                socket.sendEvent(Actions.ADD_PEER.label, new DataDTO(clientID.getSessionId().toString(), clientID.get("username").toString(), true));
            }

            socket.joinRoom(roomID);
            socketService.shareRooms();
        });


        server.addEventListener(Actions.OFFER_SDP.label, DataDTO.class, (socket, dataDTO, ackRequest) -> {
            DataDTO dto = new DataDTO(socket.getSessionId().toString(), socket.get("username").toString(), dataDTO.getSessionDescription());
            server.getClient(UUID.fromString(dataDTO.getPeerID())).sendEvent(Actions.SESSION_DESCRIPTION.label, dto);
        });

        server.addEventListener(Actions.OFFER_ICE.label, DataDTO.class, (socket, dataDTO, ackRequest) -> {
            DataDTO dto = new DataDTO(socket.getSessionId().toString(), dataDTO.getIceCandidate());
            server.getClient(UUID.fromString(dataDTO.getPeerID())).sendEvent(Actions.ICE_CANDIDATE.label, dto);
        });



        server.addConnectListener(onConnected());
        server.addDisconnectListener(socketIOClient -> {
            server.getBroadcastOperations().sendEvent(Actions.REMOVE_PEER.label, new DataDTO(socketIOClient.getSessionId().toString(), socketIOClient.get("username").toString()));
        });

        server.addEventListener(Actions.LEAVE.label, DataDTO.class, (socket, dataDTO, ackRequest) -> {
            leaveRoom(server, socketService, socket);
        });

    }

    private static void leaveRoom(SocketIOServer server, SocketService socketService, SocketIOClient socket) {
        Set<String> rooms = socket.getAllRooms();
        System.out.println(rooms);
        for (String room : rooms) {
            List<SocketIOClient> clients = new ArrayList<>();
            clients.addAll(server.getAllClients());
            clients.removeIf(socketIOClient -> !socketIOClient.getAllRooms().contains(room)); // remain only clients that are in specific room

            for (SocketIOClient client : clients) {
                server.getClient(client.getSessionId()).sendEvent(Actions.REMOVE_PEER.label, new DataDTO(socket.getSessionId().toString(), socket.get("username").toString()));
                socket.sendEvent(Actions.REMOVE_PEER.label, new DataDTO(client.getSessionId().toString(), client.get("username").toString()));
            }
            socket.leaveRoom(room);
        }
        socketService.shareRooms();
    }


    private ConnectListener onConnected() {
        return socket -> {
            socket.set("username", "guest");
        };
    }

    @PreDestroy
    public void stopServer() {
        server.stop();
    }
}
