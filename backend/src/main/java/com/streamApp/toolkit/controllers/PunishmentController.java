package com.streamApp.toolkit.controllers;

import com.streamApp.toolkit.entities.Punishment;
import com.streamApp.toolkit.service.PunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/punishment")
public class PunishmentController extends BaseController {

  private final PunishmentService punishmentService;

  @Autowired
  public PunishmentController(final PunishmentService punishmentService) {
    this.punishmentService = punishmentService;
  }

  @PostMapping("/add")
  public ResponseEntity<Punishment> addPunishment(@RequestBody final Punishment punishment) {
    return ResponseEntity.ok(punishmentService.addPunishment(punishment));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Punishment> getPunishment(@PathVariable final UUID id) {
    return ResponseEntity.ok(punishmentService.getPunishment(id));
  }

  @GetMapping("/all")
  public ResponseEntity<List<Punishment>> getAllPunishments() {
    return ResponseEntity.ok(punishmentService.getAllPunishments());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Punishment> updatePunishment(@PathVariable final UUID id, @RequestBody final Punishment punishment) {
    return ResponseEntity.ok(punishmentService.updatePunishment(id, punishment));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePunishment(@PathVariable final UUID id) {
    punishmentService.deletePunishment(id);
    return ResponseEntity.noContent().build();
  }
}
