package com.streamApp.toolkit.service;

import com.streamApp.toolkit.entities.Redemption;
import com.streamApp.toolkit.entities.enums.RedeemTypes;
import com.streamApp.toolkit.exceptions.RedemptionNotFoundException;
import com.streamApp.toolkit.repositories.RedemptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RedemptionService {

  private final RedemptionRepository redemptionRepository;

  @Autowired
  public RedemptionService(final RedemptionRepository redemptionRepository) {
    this.redemptionRepository = redemptionRepository;
  }

  public List<Redemption> getAllRedemptions() {
    return redemptionRepository.findAll();
  }

  public Optional<Redemption> getRedemptionById(final UUID id) {
    return redemptionRepository.findById(id);
  }

  public Redemption createRedemption(final Redemption redemption) {
    return redemptionRepository.save(redemption);
  }

  public Optional<Redemption> updateRedemption(final UUID id, final Redemption redemptionDetails) {
    return redemptionRepository.findById(id)
        .map(existingRedemption -> {
          existingRedemption.setName(redemptionDetails.getName());
          existingRedemption.setDescription(redemptionDetails.getDescription());
          existingRedemption.setType(redemptionDetails.getType());
          existingRedemption.setCompleted(redemptionDetails.getCompleted());
          return redemptionRepository.save(existingRedemption);
        });
  }

  public boolean deleteRedemption(final UUID id) {
    if (redemptionRepository.existsById(id)) {
      redemptionRepository.deleteById(id);
      return true;
    }
    return false;
  }

  public List<Redemption> getRedemptionsByType(final RedeemTypes type) {
    return redemptionRepository.findByType(type);
  }

  public List<Redemption> getRedemptionsByCompletedStatus(final Boolean completed) {
    return redemptionRepository.findByCompleted(completed);
  }

  public List<Redemption> getRedemptionsByTypeAndCompleted(final RedeemTypes type, final Boolean completed) {
    return redemptionRepository.findByTypeAndCompleted(type, completed);
  }

  public List<Redemption> searchRedemptionsByName(final String name) {
    return redemptionRepository.findByNameContainingIgnoreCase(name);
  }

  public Redemption markRedemptionAsCompleted(final UUID id) {
    return redemptionRepository.findById(id)
        .map(redemption -> {
          redemption.setCompleted(true);
          return redemptionRepository.save(redemption);
        })
        .orElseThrow(() -> new RedemptionNotFoundException("Redemption not found with id: " + id));
  }

  public List<Redemption> getRedemptionsByViewer(final UUID viewerId) {
    return redemptionRepository.findByViewerId(viewerId);
  }

  public List<Redemption> getRedemptionsByViewerAndCompleted(final UUID viewerId, final Boolean completed) {
    return redemptionRepository.findByViewerIdAndCompleted(viewerId, completed);
  }

  public List<Redemption> getIncompleteRedemptionsByViewer(final UUID viewerId) {
    return redemptionRepository.findByViewerIdAndCompleted(viewerId, false);
  }

  public List<Redemption> getCompletedRedemptionsByViewer(final UUID viewerId) {
    return redemptionRepository.findByViewerIdAndCompleted(viewerId, true);
  }
}
