package com.example.three.repo;

import com.example.three.model.Branches;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OfficeRepo extends CrudRepository<Branches, Long> {

  @Override
  Optional<Branches> findById(Long aLong);

  @Query(value = "SELECT *,(ABS(lon-?1)+ABS(lat-?2)) as crd\n" +
      "FROM branches ORDER BY crd ASC limit 1;", nativeQuery = true)
  Branches findAllSellersInRange(double longitude, double latitude);

}
