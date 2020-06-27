package com.example.three.controller;

import com.example.three.model.Branches;
import com.example.three.model.ErrorResponse;
import com.example.three.repo.OfficeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BranchController {

  private final OfficeRepo officeRepo;

  @GetMapping("/branches/{id}")
  public Object getBranches(@PathVariable(value = "id") Long branchId) {

    final Optional<Branches> branch = officeRepo.findById(branchId);
    if (branch.isPresent()) {
      return branch.get();
    } else {
      final ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setStatus("branch not found");
      return errorResponse;
    }
  }

  @GetMapping("/branches")
  public Object getBranches(@RequestParam("lat") String lat, @RequestParam("lon") String lon) {

    final Branches branch = officeRepo.findAllSellersInRange(Double.parseDouble(lat), Double.parseDouble(lon));

    log.info("branch {}", branch);
    if (branch == null) {
      final ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setStatus("branch not found");
      return errorResponse;
    } else {
      return branch;
    }
  }
}
