package com.example.e_event;

public class Event {
    private int id, ticket, active, ownerID;
    private String title, organizer, date_started, desc;
    private byte[] poster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDate_started() {
        return date_started;
    }

    public void setDate_started(String date_started) {
        this.date_started = date_started;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public Event( String title, String organizer, int ticket, String date_started, String desc, byte[] poster, int active, int ownerID ) {
        this.id = id;
        this.ticket = ticket;
        this.active = active;
        this.ownerID = ownerID;
        this.title = title;
        this.organizer = organizer;
        this.date_started = date_started;
        this.desc = desc;
        this.poster = poster;
    }

    public Event() {
    }


}
