package com.streamApp.toolkit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
