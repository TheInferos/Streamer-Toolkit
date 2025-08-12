package com.streamApp.toolkit.entities;

import com.streamApp.toolkit.entities.enums.RedeemTypes;
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
public class Redemption {

  @Id
  private UUID id;

  private String name;

  private String description;

  private RedeemTypes type;

  private Boolean completed;

  private UUID viewerId;

  @PrePersist
  public void assignId() {
    if (this.id == null) {
      this.id = UUID.randomUUID();
    }
  }

}
