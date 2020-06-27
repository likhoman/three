package com.example.three.service;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.example.three.mapper.Branch2BranchWithDistance;
import com.example.three.model.Branches;
import com.example.three.model.BranchesDistance;
import com.example.three.repo.BranchesRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

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
      BigDecimal x1 = BigDecimal.valueOf(lat);
      BigDecimal x2 = BigDecimal.valueOf(thecClosestBranch.getLat());
      BigDecimal y1 = BigDecimal.valueOf(lon);
      BigDecimal y2 = BigDecimal.valueOf(thecClosestBranch.getLon());

      final long longDistance = calcDistance(x1, x2, y1, y2).longValue();
      thecClosestBranch.setDistance(longDistance);
      log.info("branch {}", thecClosestBranch);
      return thecClosestBranch;
    } else {
      return null;
    }
  }

  private BigDecimal calcDistance(BigDecimal x1, BigDecimal x2, BigDecimal y1, BigDecimal y2) {
    final BigDecimal subtractX = x2.subtract(x1);
    final BigDecimal subtractY = y2.subtract(y1);
    final MathContext mathContext = new MathContext(100);
    return BigDecimalMath.sqrt(
        BigDecimalMath.pow(subtractX, 2, mathContext)
            .add(BigDecimalMath.pow(subtractY, 2, mathContext)), mathContext
    ).setScale(0, RoundingMode.CEILING);
  }
}
