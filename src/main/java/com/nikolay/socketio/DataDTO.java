package com.nikolay.socketio;

public class DataDTO {

    private String peerID;
    private String peerUsername;

    private SessionDescription sessionDescription;

    public DataDTO(String peerID, SessionDescription sessionDescription) {
        this.peerID = peerID;
        this.sessionDescription = sessionDescription;
    }

    public DataDTO(String peerID, IceCandidate iceCandidate) {
        this.peerID = peerID;
        this.iceCandidate = iceCandidate;
    }

    public SessionDescription getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(SessionDescription sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public IceCandidate getIceCandidate() {
        return iceCandidate;
    }

    public void setIceCandidate(IceCandidate iceCandidate) {
        this.iceCandidate = iceCandidate;
    }

    private IceCandidate iceCandidate;
    private String room;
    private String data;
    private boolean createOffer;

    public DataDTO(String peerID, String peerUsername) {
        this.peerID = peerID;
        this.peerUsername = peerUsername;
    }

    public DataDTO(String peerID, String peerUsername, SessionDescription sessionDescription) {
        this.peerID = peerID;
        this.peerUsername = peerUsername;
        this.sessionDescription = sessionDescription;
    }

    public DataDTO(String peerID, String peerUsername, boolean createOffer) {
        this.peerID = peerID;
        this.peerUsername = peerUsername;
        this.createOffer = createOffer;
    }

    public DataDTO(String peerID) {
        this.peerID = peerID;
    }

    public boolean isCreateOffer() {
        return createOffer;
    }

    public void setCreateOffer(boolean createOffer) {
        this.createOffer = createOffer;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPeerUsername() {
        return peerUsername;
    }

    public void setPeerUsername(String peerUsername) {
        this.peerUsername = peerUsername;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public DataDTO() {
    }

    public DataDTO(String peerID, boolean createOffer) {
        this.peerID = peerID;
        this.createOffer = createOffer;
    }


    @Override
    public String toString() {
        return "DataDTO{" +
                "peerID='" + peerID + '\'' +
                ", peerUsername='" + peerUsername + '\'' +
                ", sessionDescription=" + sessionDescription +
                ", iceCandidate=" + iceCandidate +
                ", room='" + room + '\'' +
                ", data='" + data + '\'' +
                ", createOffer=" + createOffer +
                '}';
    }
}
