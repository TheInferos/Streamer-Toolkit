package com.streamApp.toolkit.controllers;

import com.streamApp.toolkit.entities.Redemption;
import com.streamApp.toolkit.entities.enums.RedeemTypes;
import com.streamApp.toolkit.exceptions.RedemptionNotFoundException;
import com.streamApp.toolkit.service.RedemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/redemptions")
public class RedemptionController extends BaseController {

  private final RedemptionService redemptionService;

  @Autowired
  public RedemptionController(final RedemptionService redemptionService) {
    this.redemptionService = redemptionService;
  }

  @GetMapping
  public ResponseEntity<List<Redemption>> getAllRedemptions() {
    List<Redemption> redemptions = redemptionService.getAllRedemptions();
    return ResponseEntity.ok(redemptions);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Redemption> getRedemptionById(@PathVariable final UUID id) {
    return redemptionService.getRedemptionById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Redemption> createRedemption(@RequestBody final Redemption redemption) {
    Redemption createdRedemption = redemptionService.createRedemption(redemption);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdRedemption);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Redemption> updateRedemption(@PathVariable final UUID id,
                                                     @RequestBody final Redemption redemptionDetails) {
    return redemptionService.updateRedemption(id, redemptionDetails)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRedemption(@PathVariable final UUID id) {
    boolean deleted = redemptionService.deleteRedemption(id);
    if (deleted) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/type/{type}")
  public ResponseEntity<List<Redemption>> getRedemptionsByType(@PathVariable final RedeemTypes type) {
    List<Redemption> redemptions = redemptionService.getRedemptionsByType(type);
    return ResponseEntity.ok(redemptions);
  }

  @GetMapping("/completed/{completed}")
  public ResponseEntity<List<Redemption>> getRedemptionsByCompletedStatus(@PathVariable final Boolean completed) {
    List<Redemption> redemptions = redemptionService.getRedemptionsByCompletedStatus(completed);
    return ResponseEntity.ok(redemptions);
  }

  @GetMapping("/type/{type}/completed/{completed}")
  public ResponseEntity<List<Redemption>> getRedemptionsByTypeAndCompleted(@PathVariable final RedeemTypes type, 
                                                                           @PathVariable final Boolean completed) {
    List<Redemption> redemptions = redemptionService.getRedemptionsByTypeAndCompleted(type, completed);
    return ResponseEntity.ok(redemptions);
  }

  @GetMapping("/search")
  public ResponseEntity<List<Redemption>> searchRedemptionsByName(@RequestParam final String name) {
    List<Redemption> redemptions = redemptionService.searchRedemptionsByName(name);
    return ResponseEntity.ok(redemptions);
  }

  @PatchMapping("/{id}/complete")
  public ResponseEntity<Redemption> markRedemptionAsCompleted(@PathVariable final UUID id) {
    try {
      Redemption completedRedemption = redemptionService.markRedemptionAsCompleted(id);
      return ResponseEntity.ok(completedRedemption);
    } catch (RedemptionNotFoundException exception) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/viewer/{viewerId}")
  public ResponseEntity<List<Redemption>> getRedemptionsByViewer(@PathVariable final UUID viewerId) {
    List<Redemption> redemptions = redemptionService.getRedemptionsByViewer(viewerId);
    return ResponseEntity.ok(redemptions);
  }

  @GetMapping("/viewer/{viewerId}/incomplete")
  public ResponseEntity<List<Redemption>> getIncompleteRedemptionsByViewer(@PathVariable final UUID viewerId) {
    List<Redemption> redemptions = redemptionService.getIncompleteRedemptionsByViewer(viewerId);
    return ResponseEntity.ok(redemptions);
  }

  @GetMapping("/viewer/{viewerId}/completed")
  public ResponseEntity<List<Redemption>> getCompletedRedemptionsByViewer(@PathVariable final UUID viewerId) {
    List<Redemption> redemptions = redemptionService.getCompletedRedemptionsByViewer(viewerId);
    return ResponseEntity.ok(redemptions);
  }
}
