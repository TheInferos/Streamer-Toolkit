package com.streamApp.toolkit.service;

import com.streamApp.toolkit.entities.Punishment;
import com.streamApp.toolkit.repositories.PunishmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PunishmentService {

  private final PunishmentRepository punishmentRepository;

  @Autowired
  public PunishmentService(final PunishmentRepository punishmentRepository) {
    this.punishmentRepository = punishmentRepository;
  }

  public Punishment addPunishment(final Punishment punishment) {
    return punishmentRepository.save(punishment);
  }

  public Punishment getPunishment(final UUID id) {
    return punishmentRepository.findById(id).orElse(null);
  }

  public List<Punishment> getAllPunishments() {
    return punishmentRepository.findAll();
  }
}
