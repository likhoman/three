package com.example.three.repo;

import com.example.three.model.Branches;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OfficeRepo extends CrudRepository<Branches, Long> {

  @Override
   Optional<Branches> findById(Long aLong);

}
