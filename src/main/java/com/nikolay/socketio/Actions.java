package com.nikolay.socketio;

public enum Actions {
    JOIN("join"),
    LEAVE("leave"),
    SHARE_ROOMS("share-rooms"),

    ADD_PEER("add-peer"),
    REMOVE_PEER("remove-peer"),
    OFFER_ICE("offer-ice"),
    OFFER_SDP("offer-sdp"),
    ICE_CANDIDATE("ice-candidate"),
    SESSION_DESCRIPTION("session-description");

    public final String label;

    private Actions(String label) {
        this.label = label;
    }
}
