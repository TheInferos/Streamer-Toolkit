package com.streamApp.toolkit.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
public class Punishment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  private String name;

  private int weight;

  @PrePersist
  public void assignId() {
    if (this.id == null) {
      this.id = UUID.randomUUID();
    }
  }
}
