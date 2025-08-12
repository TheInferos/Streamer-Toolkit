package com.streamApp.toolkit.repositories;

import com.streamApp.toolkit.entities.Redemption;
import com.streamApp.toolkit.entities.enums.RedeemTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RedemptionRepository extends JpaRepository<Redemption, UUID> {
  
  List<Redemption> findByType(RedeemTypes type);
  
  List<Redemption> findByCompleted(Boolean completed);
  
  List<Redemption> findByTypeAndCompleted(RedeemTypes type, Boolean completed);
  
  List<Redemption> findByNameContainingIgnoreCase(String name);
  
  List<Redemption> findByViewerId(UUID viewerId);
  
  List<Redemption> findByViewerIdAndCompleted(UUID viewerId, Boolean completed);
}
