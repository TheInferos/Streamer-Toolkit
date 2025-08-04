package com.stream_app.toolkit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;


import java.util.UUID;

@Entity
public class Viewer {

    @Id
    private UUID id;

    @Column
    private String twitchHandle;

    @Column
    private String name;

    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTwitchHandle() {
        return twitchHandle;
    }

    public void setTwitchHandle(String twitchHandle) {
        this.twitchHandle = twitchHandle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
