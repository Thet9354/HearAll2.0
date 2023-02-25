package com.example.hearlall.Model;

public class Events {

    private int id;
    private int eventImage;
    private int hostImage;

    private String eventTitle;
    private String eventHost;
    private String hostDesc;
    private String eventParticipants;
    private String eventLink;

    public Events(int id, int eventImage, int hostImage, String eventTitle, String eventHost, String hostDesc, String eventParticipants, String eventLink) {
        this.id = id;
        this.eventImage = eventImage;
        this.hostImage = hostImage;
        this.eventTitle = eventTitle;
        this.eventHost = eventHost;
        this.hostDesc = hostDesc;
        this.eventParticipants = eventParticipants;
        this.eventLink = eventLink;
    }

    public Events() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventImage() {
        return eventImage;
    }

    public void setEventImage(int eventImage) {
        this.eventImage = eventImage;
    }

    public int getHostImage() {
        return hostImage;
    }

    public void setHostImage(int hostImage) {
        this.hostImage = hostImage;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventHost() {
        return eventHost;
    }

    public void setEventHost(String eventHost) {
        this.eventHost = eventHost;
    }

    public String getHostDesc() {
        return hostDesc;
    }

    public void setHostDesc(String hostDesc) {
        this.hostDesc = hostDesc;
    }

    public String getEventParticipants() {
        return eventParticipants;
    }

    public void setEventParticipants(String eventParticipants) {
        this.eventParticipants = eventParticipants;
    }

    public String getEventLink() {
        return eventLink;
    }

    public void setEventLink(String eventLink) {
        this.eventLink = eventLink;
    }
}
