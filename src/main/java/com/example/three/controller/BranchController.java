package com.example.three.controller;

import com.example.three.model.Branches;
import com.example.three.model.BranchesDistance;
import com.example.three.model.ErrorResponse;
import com.example.three.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class BranchController {

  private final BranchService branchService;

  @GetMapping("/branches/{id}")
  public Object getBranches(@PathVariable(value = "id") Long branchId) {

    final Branches branch = branchService.getBranch(branchId);
    return getResponse(branch);
  }

  @GetMapping("/branches")
  public Object getBranches(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon) {

    final BranchesDistance branchDistance = branchService.getBranchDistance(lat, lon);
    return getResponse(branchDistance);
  }

  private Object getResponse(Object branchDistance) {
    if (branchDistance == null) {
      final ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setStatus("branch not found");
      return errorResponse;
    } else {
      return branchDistance;
    }
  }
}
