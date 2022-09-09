package com.example.hearlall.messages;

import com.example.hearlall.AudioSessionInfo;

public class AudioSessionEvent {
    public final boolean open;
    public final AudioSessionInfo session;

    public AudioSessionEvent(boolean open, AudioSessionInfo session) {
        this.open = open;
        this.session = session;
    }

    @Override
    public String toString() {
        return "AudioSessionEvent{" +
                "open=" + open +
                ", session=" + session +
                '}';
    }
}
