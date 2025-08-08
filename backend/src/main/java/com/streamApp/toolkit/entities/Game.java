package com.streamApp.toolkit.entities;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

  @Id
  private UUID id;

  private String name;

  @ElementCollection
  private List<String> genreList;

  @PrePersist
  public void assignId() {
    if (this.id == null) {
      this.id = UUID.randomUUID();
    }
  }
}
