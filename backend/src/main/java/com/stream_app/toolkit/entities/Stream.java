package com.stream_app.toolkit.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stream {
    @Id
    private UUID id;

    private String name;
    private String description;
    private String url;
    
    @ElementCollection
    private List<String> games;
    
    @OneToMany
    @JoinColumn(name = "stream_id")
    private List<Viewer> viewers;
    
    @ElementCollection
    private List<String> tags;
    
    @ElementCollection
    private List<String> categories;
    
    @PrePersist
    public void assignId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}
