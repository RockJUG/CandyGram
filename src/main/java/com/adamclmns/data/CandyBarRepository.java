package com.adamclmns.data;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface CandyBarRepository extends Repository<CandyBar, Long> {

    List<CandyBar> findAll();

    CandyBar findById(Long id);

    CandyBar save(CandyBar candyBar);

    List<CandyBar> findByNameIsLike(String name);

    List<CandyBar> findAllByManufacturerIsLike(String manufacturer);

    List<CandyBar> findAllByDistributionContaining(String distribution);
}
