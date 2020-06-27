package com.example.three.repo;

import com.example.three.model.Branches;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BranchesRepo extends CrudRepository<Branches, Long> {

  @Override
  Optional<Branches> findById(Long branchId);

  @Query(value = "SELECT *,(ABS(lon-?2)+ABS(lat-?1)) as crd\n" +
      "FROM branches ORDER BY crd ASC limit 1;", nativeQuery = true)
  Branches findThecClosestBranch(double longitude, double latitude);

}
