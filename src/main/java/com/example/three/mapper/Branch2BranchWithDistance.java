package com.example.three.mapper;

import com.example.three.model.Branches;
import com.example.three.model.BranchesDistance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Branch2BranchWithDistance {
  Branch2BranchWithDistance MAPPER = Mappers.getMapper(Branch2BranchWithDistance.class);

  BranchesDistance mapToBranchesDistance(Branches branches);
}
