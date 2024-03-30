# WebRTC Video Conference

This is the backend part of a full-stack project - WebRTC Video Conference.

It is an **Java Spring** application, that uses **Socket.IO** for connecting peers (users).

The server also includes **JWT authentication**, sending emails for verification and **PostgreSQL** database connectivity.

## WebRTC

WebRTC is a technology that allows you to connect two or more users without sending their data to the server. The server is needed for signaling at the start of a video conference. Once clients are connected **no data** gets sent to the server.

<img width="1792" alt="Screenshot 2024-02-23 at 16 32 58" src="https://github.com/lavrentyevn/webrtc-video-conference-backend/assets/111048277/0330471d-a638-4a12-a83a-7f16e44428a0">

WebRTC handshake consists of the following steps

- First user creates a Peer Connection with a list of ICE Servers (they are used to translate NAT into real IP address and vice versa), sets it as his local descriptor, Creates Offer and send his ICE data.
- Second user also creates a Peer Connection with a list of Ice Servers and sets it as his local descriptor. Then he sets Offer as his remote descriptor and adds ICE data as a canditate. Finally he creates an Answer and sends his ICE data.
- First user adds ICE data as a candidate and sets Answer as his remote descriptor.

## Socket io

Socket io is a library that enables real-time, bi-directional communication between clients and servers. In this project it helps to connect users via WebRTC handshake that was explained earlier. This are actions that socket io uses to emit data in this application.

```

const ACTIONS = {
  JOIN: "join",
  LEAVE: "leave",
  SHARE_ROOMS: "share-rooms",
  ADD_PEER: "add-peer",
  REMOVE_PEER: "remove-peer",
  OFFER_SDP: "offer-sdp",
  OFFER_ICE: "offer-ice",
  ICE_CANDIDATE: "ice-candidate",
  SESSION_DESCRIPTION: "session-description",
};

```

For example, this a method that sends ICE data.

```

function sendIce(socket) {
    return ({ peerID, iceCandidate }) => {
      io.to(peerID).emit(ACTIONS.ICE_CANDIDATE, {
        peerID: socket.id,
        iceCandidate,
      });
    };
  }

socket.on(ACTIONS.OFFER_ICE, sendIce(socket));

```

## JWT authentication

In order to use a video conferencing site you have to create an account. It can be either a **client** (requires username and password) or a **guest** (requires only email verification, lasts for 30 minutes) account. 

After logging into an account, the server returns an access token (in a response).

<img width="737" alt="Screenshot 2024-03-30 at 15 07 07" src="https://github.com/lavrentyevn/webrtc-video-conference-backend-javaspring/assets/111048277/feca0659-7a37-494d-b84f-2326be6c587b">

It also returns a refresh token (in a cookie).

<img width="733" alt="Screenshot 2024-03-30 at 15 08 05" src="https://github.com/lavrentyevn/webrtc-video-conference-backend-javaspring/assets/111048277/31713120-c927-47c6-97bd-c79a580c4404">

It is important to note that passwords are **not** stored in a decoded state. Any password is stored as a **hash**, which can be obtained by a password hashing algorithm, such as **bcrypt**.

<img width="543" alt="Снимок экрана 2023-11-13 в 12 47 03" src="https://github.com/lavrentyevn/authbackend/assets/111048277/f35767ed-8796-462b-9947-07dc757277e1">

List of API:
- POST ("/guest", {email}) creating guest
- PUT ("/guest/verify?token=") login guest

- POST ("/client", {email, username, password}) creating client
- POST ("/client/login", {username, password}) login client
- PUT ("/client/verify?token=") verify client

- PUT ("/logout") logout client || guest

- GET ("/refresh") refresh client || guest

- POST ("/room", {name, password, description, creator}) creating room
- POST ("/room/access", {name, password}) accessing room
- POST ("/room/check", {username}) check rooms

- POST ("/message", {roomname, email, message}) logging message

- POST ("/invitation", {email: [], room}) create invitation
- POST ("/invitation/check", {email, room}) check invitation

- POST ("/event", {room}) create event
- POST ("/event/log", {email, name, move}) log event

## Email verification

Clients and guests have to verify their accounts after creating them.

<img width="790" alt="Screenshot 2024-02-23 at 17 03 22" src="https://github.com/lavrentyevn/webrtc-video-conference-backend/assets/111048277/cb3b54db-873f-41ea-8631-621e2580cf68">

## PostgreSQL 

This is the diagram that represents all tables in the database.

<img width="825" alt="Screenshot 2024-03-30 at 14 53 32" src="https://github.com/lavrentyevn/webrtc-video-conference-backend-javaspring/assets/111048277/06a509de-d3a4-4a60-b905-4df67d094896">

As you can see messages that are send in rooms are stored in a database, as well as email verification. Users can be invited to rooms, which means that they don't have to insert a password when trying to access it. Rooms can also be *Event Rooms*; such rooms are accessible only through invitations.

## Result

This is the backend part of a full stack project. Here is the link to the front end React application https://github.com/lavrentyevn/webrtc-video-conference-frontend

Some screenshots of the full stack application.

<img width="1776" alt="Screenshot 2024-02-25 at 14 33 30" src="https://github.com/lavrentyevn/webrtc-video-conference-frontend/assets/111048277/7fd77be6-6dfb-4b2a-849b-74c2b35b0c28">

<img width="1792" alt="Screenshot 2024-02-23 at 18 25 56" src="https://github.com/lavrentyevn/webrtc-video-conference-frontend/assets/111048277/1d46876d-3259-4f5b-a803-638821784c51">
