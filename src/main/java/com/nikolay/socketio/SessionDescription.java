package com.nikolay.socketio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SessionDescription {
    @JsonProperty("type")
    private String type;
    @JsonProperty("sdp")
    private String sdp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSdp() {
        return sdp;
    }

    public void setSdp(String sdp) {
        this.sdp = sdp;
    }

    public SessionDescription() {
    }

    public SessionDescription(String type, String sdp) {
        this.type = type;
        this.sdp = sdp;
    }
}
