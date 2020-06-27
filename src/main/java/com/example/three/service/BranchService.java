package com.example.three.service;

import com.example.three.mapper.Branch2BranchWithDistance;
import com.example.three.model.Branches;
import com.example.three.model.BranchesDistance;
import com.example.three.repo.BranchesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class BranchService {

  private final BranchesRepo branchesRepo;

  public Branches getBranch(Long branchId) {
    return branchesRepo.findById(branchId).orElse(null);
  }

  public BranchesDistance getBranchDistance(Double lat, Double lon) {
    final Branches branches = branchesRepo
        .findThecClosestBranch(lat, lon);
    final BranchesDistance thecClosestBranch = Branch2BranchWithDistance
        .MAPPER.mapToBranchesDistance(branches);
    if (thecClosestBranch != null) {
      Double x1 = lat;
      Double x2 = thecClosestBranch.getLat();
      Double y1 = lon;
      Double y2 = thecClosestBranch.getLon();

      final double calcDistance = calcDistance(x1, x2, y1, y2);
      final long distance = (long) calcDistance;
      thecClosestBranch.setDistance(distance);
      log.info("branch {}", thecClosestBranch);
      return thecClosestBranch;
    } else {
      return null;
    }
  }

  private double calcDistance(Double x1, Double x2, Double y1, Double y2) {

    final int rEarth = 6371;

    double latDistance = Math.toRadians(x2 - x1);
    double lonDistance = Math.toRadians(y2 - y1);

    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(x1)) * Math.cos(Math.toRadians(x2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return rEarth * c * 1000;
  }
}
