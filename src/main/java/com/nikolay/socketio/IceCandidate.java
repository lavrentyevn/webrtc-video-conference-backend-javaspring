package com.nikolay.socketio;

public class IceCandidate {
    private String candidate;
    private String sdpMid;
    private Integer sdpMLineIndex;
    private String usernameFragment;

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public String getSdpMid() {
        return sdpMid;
    }

    public void setSdpMid(String sdpMid) {
        this.sdpMid = sdpMid;
    }

    public Integer getSdpMLineIndex() {
        return sdpMLineIndex;
    }

    public void setSdpMLineIndex(Integer sdpMLineIndex) {
        this.sdpMLineIndex = sdpMLineIndex;
    }

    public String getUsernameFragment() {
        return usernameFragment;
    }

    public void setUsernameFragment(String usernameFragment) {
        this.usernameFragment = usernameFragment;
    }

    public IceCandidate() {
    }

    public IceCandidate(String candidate, String sdpMid, Integer sdpMLineIndex, String usernameFragment) {
        this.candidate = candidate;
        this.sdpMid = sdpMid;
        this.sdpMLineIndex = sdpMLineIndex;
        this.usernameFragment = usernameFragment;
    }
}
